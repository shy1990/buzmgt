package com.wangge.buzmgt.cash.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.cash.entity.BankTrade;
import com.wangge.buzmgt.cash.entity.Cash.CashStatusEnum;
import com.wangge.buzmgt.cash.entity.WaterOrderCash.WaterPayStatusEnum;
import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.cash.repository.BankTradeRepository;
import com.wangge.buzmgt.cash.repository.WaterOrderCashRepository;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.salesman.entity.BankCard;
import com.wangge.buzmgt.salesman.entity.SalesmanData;
import com.wangge.buzmgt.salesman.service.SalesmanDataService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.SearchFilter;
import com.wangge.buzmgt.util.excel.ExcelImport;
import com.wangge.buzmgt.util.file.FileUtils;

@Service
public class BankTradeServiceImpl implements BankTradeService {

  @Value("${buzmgt.file.fileUploadPath}")
  private String fileUploadPath;

  private static final Logger logger = Logger.getLogger(BankTradeServiceImpl.class);
  @Resource
  private BankTradeRepository bankTradeRepository;
  @Resource
  private WaterOrderCashService waterOrderCashService;
  @Resource
  private SalesmanDataService dataService;
  @Resource
  private RegionService regionService;

  @Override
  public List<BankTrade> findAll(Map<String, Object> searchParams) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<BankTrade> spec = bankTradeSearchFilter(filters.values(), BankTrade.class);
    List<BankTrade> bankTrades = bankTradeRepository.findAll(spec);
    return bankTrades;

  }

  @Override
  public Page<BankTrade> findAll(Map<String, Object> searchParams, Pageable pageRequest) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<BankTrade> spec = bankTradeSearchFilter(filters.values(), BankTrade.class);
    Page<BankTrade> bankTradePage = bankTradeRepository.findAll(spec, pageRequest);
    return bankTradePage;
  }

  @Override
  public List<BankTrade> findByCreateDate(String createDate) {
    return null;
  }

  @Override
  public void delete(BankTrade bankTrade) {
    bankTradeRepository.delete(bankTrade);
  }

  @Override
  public JSONObject importExcel(HttpServletRequest request, String importDate) {

    JSONObject jsonObject = new JSONObject();

    MultipartHttpServletRequest mReq;
    MultipartFile file;
    InputStream is;

    // ============查询是否归档+是否已经审核账单。
    Map<String, Object> searchParams = new HashMap<>();
    searchParams.put("EQ_payDate", importDate);
    searchParams.put("EQ_isArchive", 1);
    List<BankTrade> bankTrades = this.findAll(searchParams);
    searchParams.remove("EQ_isArchive");

    // 已归档
    if (bankTrades.size() > 0) {
      jsonObject.put("result", "failure");
      jsonObject.put("message", "已归档不能导入");
      return jsonObject;
    }

    // 已审核
    searchParams.put("EQ_payStatus", WaterPayStatusEnum.OverPay);
    List<WaterOrderCash> orderCashs = waterOrderCashService.findAll(searchParams);
    searchParams.remove("EQ_payStatus");
    if (orderCashs.size() > 0) {
      jsonObject.put("result", "failure");
      jsonObject.put("message", "已审核不能导入");
      return jsonObject;
    }

    // 原始文件名称
    String fileName;

    String fileRealPath = "";
    try {

      mReq = (MultipartHttpServletRequest) request;

      // 获取文件
      file = mReq.getFile("file-input");

      // 取得文件的原始文件名称
      fileName = file.getOriginalFilename();

      logger.info("取得原始文件名:" + fileName);

      String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

      logger.info("原始文件的后缀名:" + suffix);

      if (!"xlsx".equals(suffix) && !"xls".equals(suffix)) {
        jsonObject.put("result", "failure");
        jsonObject.put("message", "文件类型错误，请选择xlsx类型的文件");

        FileUtils.deleteFile(fileUploadPath + fileName);

        return jsonObject;
      }

      if (!StringUtils.isEmpty(fileName)) {
        is = file.getInputStream();

        fileName = FileUtils.reName(fileName);

        logger.info("重新命名后的文件名:" + fileName);

        FileUtils.saveFile(fileName, is, fileUploadPath);

        fileRealPath = fileUploadPath + fileName;

        // 文件上传完成后，读取文件
        Map<Integer, String> excelContent = ExcelImport.readExcelContent(fileRealPath);

        // ============读取文件完成后，导入到数据库=============
        bankTrades = this.findAll(searchParams);
        if (bankTrades.size() > 0) {
          this.delete(bankTrades);
        }
        // TODO 查询数据是否已经归档，否则不能经行导入保存。
        this.save(excelContent, importDate);

        FileUtils.deleteFile(fileRealPath);

        jsonObject.put("result", "success");
      } else {
        throw new IOException("文件名为空!");
      }

    } catch (Exception e) {
      FileUtils.deleteFile(fileRealPath);
      jsonObject.put("result", "failure");
      logger.info(e.getMessage());
      return jsonObject;

    }
    return jsonObject;
  }

  @Override
  public List<BankTrade> save(Map<Integer, String> excelContent, String importDate) {
    List<BankTrade> bankTrades = new ArrayList<>();
    final Date date = DateUtil.string2Date(importDate);
    excelContent.forEach((integer, s) -> {
      BankTrade bt = new BankTrade();
      String[] content = s.split("-->");
      if (!"空".equals(content[0])) {

        bt.setPayDate(DateUtil.string2Date(content[0]));
        Float money = content[1] == "" ? new Float(0) : new Float(content[1]);
        bt.setMoney(money);
        bt.setCardName(content[2]);
        bt.setCardNo(content[3]);
        bt.setBankName(content[4]);
        bt.setImportDate(date);

        // 核对业务员打款基表进行核对，添加userId
        setUserIdForBankTrade(bt);

        bankTrades.add(bt);
      }

    });
    this.save(bankTrades);

    return null;
  }

  public void setUserIdForBankTrade(BankTrade bankTrade) {
    String userId = null;
    String CardName = bankTrade.getCardName();
    String CardNo = bankTrade.getCardNo();
    SalesmanData s = dataService.findByNameAndCard_cardNumber(CardName, CardNo);
    if (s != null) {
      List<BankCard> bankCards = s.getCard();
      for (BankCard bc : bankCards) {
        if (CardNo.equals(bc.getCardNumber())) {
          userId = s.getUserId();
        }
      }
    }
    bankTrade.setUserId(userId);
  }

  @Override
  public List<BankTrade> save(List<BankTrade> bankTrades) {
    return bankTradeRepository.save(bankTrades);
  }

  @Override
  public void delete(List<BankTrade> bankTrades) {
    bankTradeRepository.delete(bankTrades);
  }

  private static Specification<BankTrade> bankTradeSearchFilter(final Collection<SearchFilter> filters,
      final Class<BankTrade> entityClazz) {

    return new Specification<BankTrade>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_ORDERSIGNFOR_TYPE = "com.wangge.buzmgt.cash.entity.Cash$CashStatusEnum";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public Predicate toPredicate(Root<BankTrade> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (CollectionUtils.isNotEmpty(filters)) {
          List<Predicate> predicates = new ArrayList<Predicate>();
          for (SearchFilter filter : filters) {
            // nested path translate, 如Task的名为"user.name"的filedName,
            // 转换为Task.user.name属性
            String[] names = StringUtils.split(filter.fieldName, ".");
            Path expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
              expression = expression.get(names[i]);
            }

            String javaTypeName = expression.getJavaType().getName();

            // logic operator
            switch (filter.operator) {
            case EQ:
              // 日期相等,小于等于最大值,大于等于最小值
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  predicates.add(cb.greaterThanOrEqualTo(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
                  predicates.add(cb.lessThanOrEqualTo(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
                } catch (ParseException e) {
                  throw new RuntimeException("日期格式化失败!");
                }
              } else if (javaTypeName.equals(TYPE_ORDERSIGNFOR_TYPE)) {
                String status = filter.value.toString();
                if (CashStatusEnum.UnPay.toString().equals(status)) {
                  filter.value = CashStatusEnum.UnPay;
                }
                if (CashStatusEnum.OverPay.toString().equals(status)) {
                  filter.value = CashStatusEnum.OverPay;
                }
                predicates.add(cb.equal(expression, filter.value));
              } else {
                predicates.add(cb.equal(expression, filter.value));
              }

              break;
            case LIKE:
              predicates.add(cb.like(expression, "%" + filter.value + "%"));

              break;
            case GT:
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  // 大于最大值
                  predicates.add(cb.greaterThan(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
                } catch (ParseException e) {
                  throw new RuntimeException("日期格式化失败!");
                }
              } else {
                predicates.add(cb.greaterThan(expression, (Comparable) filter.value));
              }

              break;
            case LT:
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  // 小于最小值
                  predicates.add(cb.lessThan(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
                } catch (ParseException e) {
                  throw new RuntimeException("日期格式化失败!");
                }
              } else {
                predicates.add(cb.lessThan(expression, (Comparable) filter.value));
              }

              break;
            case GTE:
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  // 大于等于最小值
                  predicates.add(cb.greaterThanOrEqualTo(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
                } catch (ParseException e) {
                  e.printStackTrace();
                }
              } else if (javaTypeName.equals(TYPE_ORDERSIGNFOR_TYPE)) {
                String status = filter.value.toString();
                if (CashStatusEnum.UnPay.toString().equals(status)) {
                  filter.value = CashStatusEnum.UnPay;
                }
                if (CashStatusEnum.OverPay.toString().equals(status)) {
                  filter.value = CashStatusEnum.OverPay;
                }
                predicates.add(cb.greaterThanOrEqualTo(expression, (Comparable) filter.value));

              } else {
                predicates.add(cb.greaterThanOrEqualTo(expression, (Comparable) filter.value));
              }

              break;
            case LTE:
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  // 小于等于最大值
                  predicates.add(cb.lessThanOrEqualTo(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
                } catch (ParseException e) {
                  throw new RuntimeException("日期格式化失败!");
                }
              } else {
                predicates.add(cb.lessThanOrEqualTo(expression, (Comparable) filter.value));
              }

              break;
            case NOTEQ:

              predicates.add(cb.notEqual(expression, filter.value));

              break;
            case ISNULL:
              boolean value = Boolean.parseBoolean("true");
              if (value)
                predicates.add(cb.isNull(expression));
              else
                predicates.add(cb.isNotNull(expression));

              break;
            case ORMLK:
              /**
               * sc_ORMLK_userId = 370105,3701050,3701051 用于区域选择
               */
              String[] parameterValue = ((String) filter.value).split(",");
              Predicate[] pl = new Predicate[parameterValue.length];

              for (int n = 0; n < parameterValue.length; n++) {
                pl[n] = (cb.like(expression, "%" + parameterValue[n] + "%"));
              }

              Predicate p_ = cb.or(pl);
              predicates.add(p_);
              break;

            default:
              break;

            }
          }

          // 将所有条件用 and 联合起来
          if (!predicates.isEmpty()) {
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
          }
        }

        return cb.conjunction();
      }
    };
  }

}
