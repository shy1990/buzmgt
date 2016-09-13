<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="/static/income/phone.css">
<link rel="stylesheet" type="text/css"
	href="/static/income/plan_index.css" />

<div class="modal-dialog" role="document" style="width: 980px;">
	<div class="modal-content modal-blue">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<h3 class="modal-title">其他方案适用人员</h3>
		</div>
		<div class="modal-body">
			<div class="row">
				<!--col begin-->
				<div class="col-md-12">
					<!--orderbox begin-->
					<div class="order-box">
						<ul>
							<li>
								<dl class="dl-horizontal">
									<dt>选择所属省份：</dt>
									<dd>
										<span class="text-pronce" id="otherRegionName"></span>
									</dd>
								</dl>


							</li>


							<li>
								<dl class="dl-horizontal">
									<dt>填写方案标题：</dt>
									<dd>
										<span class="text-pronce" id="otherMaintitle"></span>
									</dd>
								</dl>


							</li>

							<li>
								<dl class="dl-horizontal">
									<dt>填写副标题：</dt>
									<dd>
										<span class="text-pronce" id="otherSubtitle"></span>

									</dd>
								</dl>
							</li>

							<li>
								<dl class="dl-horizontal">
									<dt>使用人员：</dt>

									<dd style="width: 750px; margin-bottom: 20px">

										<div id='otherUserList' class="col-sm-2"></div>

									</dd>
								</dl>
							</li>
						</ul>
					</div>
					<!--orderobx end-->
				</div>
			</div>
		</div>
		<!--col end-->
	</div>
</div>
<script src="/static/income/main/otherPlan.js" type="text/javascript"
	charset="utf-8"></script>
<script src='/static/js/dateutil.js'></script>
<script type="text/javascript">
	
</script>



</html>