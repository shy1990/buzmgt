package com.wangge.buzmgt.rejection.service;

import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.rejection.entity.RejectStatusEnum;
import com.wangge.buzmgt.rejection.entity.Rejection;
import com.wangge.buzmgt.rejection.repository.RejectionRepository;
import com.wangge.buzmgt.util.SearchFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class RejectionServiceImpl implements RejectionServive {
    @Resource
    private RejectionRepository rejectionRepository;
    @Resource
    private RegionService regionService;

    @Override
    public Page<Rejection> findAll(Map<String, Object> searchParams, Pageable pageRequest) {
        regionService.disposeSearchParams("userId",searchParams);
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<Rejection> spec = RejectSearchFilter(filters.values(), Rejection.class);
        Page<Rejection> rejectionPage = rejectionRepository.findAll(spec,pageRequest);
        List<Rejection> rejectionList = rejectionPage.getContent();
        if (CollectionUtils.isNotEmpty(rejectionList)){
            rejectionList.forEach(list -> {
                if (RejectStatusEnum.recGoods.equals(list.getStatus())){
                    list.setView(true);
                }
            });
        }
        return rejectionPage;
    }

    @Override
    public List<Rejection> findAll(Map<String, Object> searchParams) {
        regionService.disposeSearchParams("userId",searchParams);
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<Rejection> spec = RejectSearchFilter(filters.values(), Rejection.class);
        List<Rejection> rejectionList = rejectionRepository.findAll(spec);
        return rejectionList;
    }

    private static Specification<Rejection> RejectSearchFilter(final Collection<SearchFilter> filters,
                                                        final Class<Rejection> entityClazz) {

        return new Specification<Rejection>() {

            private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

            private final static String TIME_MIN = " 00:00:00 000";

            private final static String TIME_MAX = " 23:59:59 999";

            private final static String TYPE_DATE = "java.util.Date";

            @SuppressWarnings({ "rawtypes", "unchecked" })
            @Override
            public Predicate toPredicate(Root<Rejection> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
                                } else {
                                    predicates.add(cb.equal(expression, filter.value));
                                }

                                break;
                            case LIKE:
                                predicates.add(cb.like(expression, "%" + filter.value + "%"));

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

                                predicates.add(cb.isNull(expression));
                                break;
                            case NOTNULL:
                                predicates.add(cb.isNull(expression));

                                break;
                            case ORMLK:
                                /**
                                 * sc_ORMLK_userId = 370105,3701050,3701051
                                 * 用于区域选择
                                 */
                                String[] parameterValue = ((String) filter.value).split(",");
                                Predicate[] pl=new Predicate[parameterValue.length];

                                for(int n=0;n<parameterValue.length;n++){
                                    pl[n]=(cb.like(expression, "%"+parameterValue[n]+"%"));
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

    @Override
    public Rejection save(Rejection rejection) {
        return rejectionRepository.save(rejection);
    }
}
