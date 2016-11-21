<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 	System.out.println(basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>管理系统</title>
<base href="<%=basePath%>">
<%
	if(session.getAttribute("currentUser")==null){
		response.sendRedirect("index_1.jsp");
		return;
	}
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.4.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.4.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.4.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">
	$(function(){
		var treeData = [{
			text:"货运管理",
			children:[{
				text:"货主管理",
				attributes:{
					url:"${pageContext.request.contextPath}/pages/admin/providerManage.jsp"
				}
			},{
				text:"司机管理",
				attributes:{
					url:"${pageContext.request.contextPath}/pages/admin/driversManage.jsp"
				}
			},{
				text:"货物类别管理",
				attributes:{
					url:"${pageContext.request.contextPath}/pages/admin/goodsTypeManage.jsp"
				}
			},
			{
				text:"入库",
				attributes:{
					url:"${pageContext.request.contextPath}/pages/admin/importManage.jsp"
				}
			},
//			{
//				text:"货物管理",
//				attributes:{
//					url:"${pageContext.request.contextPath}/pages/admin/goodsManage.jsp"
//				}
//			},{
//				text:"货物入库管理",
//				attributes:{
//					url:"${pageContext.request.contextPath}/pages/admin/importManage.jsp"
//				}
//			},{
//				text:"货物库存管理",
//				attributes:{
//					url:"${pageContext.request.contextPath}/pages/admin/stockManage.jsp"
//				}
//			},
			{
				text:"出库记录管理",
				attributes:{
					url:"${pageContext.request.contextPath}/pages/admin/exportManage.jsp"
				}
			},
			{
				text:"库存",
				attributes:{
					url:"${pageContext.request.contextPath}/pages/admin/stockManage.jsp"
				}
			}
			]
		}];
		
		$("#tree").tree({
			data:treeData,
			lines:true,
			onClick:function(node){
				if(node.attributes){
					openTab(node.text,node.attributes.url);
				}
			}
		});
		
		function openTab(text,url){
			if($("#tabs").tabs('exists',text)){
				$("#tabs").tabs('select',text);
			}else{
				var content="<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="+url+"></iframe>";
				$("#tabs").tabs('add',{
					title:text,
					closable:true,
					content:content
				});
			}
		}
	});
</script>
</head>
<body class="easyui-layout">
	<div region="north" style="height:80px; background-color:#105d95;">
		<div align="left" style="width: 80%;float:left"><img src=""></div>
		<div style="padding-top:10px;padding-right:50px;">当前用户：<font color="red" size="6px">${currentUser.userName }</font>
			<a href="stockManageSystem/login!logOut">>><font color="red">注销</font></a>
		</div>
	</div>
	
	<div region="west" style="width:150px;" title="导航菜单" split="true">
		<ul id="tree">
		
		</ul>
	</div>
	
	<div region="center" class="easyui-tabs" fit="true" border="false" id="tabs">
		<div title="首页" >
			<div align="center" style="padding-top: 100px;"><font color="red" size="10">欢迎使用货运管理系统</font></div>		
		</div>
	</div>
	
	
</body>
</html>