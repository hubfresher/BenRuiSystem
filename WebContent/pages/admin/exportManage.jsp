<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>运输管理系统</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.4.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery-easyui-1.4.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.4.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery-easyui-1.4.3/datagrid-detailview.js"></script>
<script type="text/javascript">
	var url;
	
	function searchExport1(){
		$('#dg1').datagrid('load',{
			//s_goodsId:$('#s_goodsId').val(),
			//s_goodsName:$('#s_goodsName').val(),
			//s_bexpoPrice:$('#s_bexpoPrice').val(),
			s_providerName:$('#s_providerName').val(),
			s_bexpoDate:$('#s_bexpoDate').datebox("getValue"),
			s_eexpoDate:$('#s_eexpoDate').datebox("getValue"),
		});
	}
	
	$(function () {
		$('#dg1').datagrid({
			view: detailview,
			detailFormatter: function(rowIndex, rowData){
				return '<div style="padding:2px"><table id="ddv-' + rowIndex + '"></table></div>'; 
			},
		 onExpandRow:function(index,row){//注意3  
             $('#ddv-'+index).datagrid({  
                 url:'${pageContext.request.contextPath}/stockManageSystem/export!getExportItem?id='+(row.id),  
                 fitColumns:true,  
                 singleSelect:true,  
                 height:'auto',  
                 columns:[[  
                     {field:'exportid',title:'出库编号'},  
                     {field:'proName',title:'出库公司',width:50},
                     {field:'typeName',title:'出库货物名称',width:50},  
                     {field:'goodsnum',title:'出库数量',width:100}  
                 ]],  
                 onResize:function(){  
                     $('#dg1').datagrid('fixDetailRowHeight',index);  
                 },  
                 onLoadSuccess:function(){  
                     setTimeout(function(){  
                         $('#dg1').datagrid('fixDetailRowHeight',index);  
                     },0);  
                 }  
             });  
             $('#dg1').datagrid('fixDetailRowHeight',index);  
         }  
		});
		
		
        $("input:radio[name=ischarge]").change(function () {
        	var val=$('input:radio[name="ischarge"]:checked').val();
        	 if(val=='true'){
        		 $('#payDate').datebox({ disabled: false});
        		          }
        	else
        		{
        		 $('#payDate').datebox({ disabled: true});
        		}
           
        });
        
        $("input:radio[name=isbackbill]").change(function () {
        	var val=$('input:radio[name="isbackbill"]:checked').val();
        	 if(val=='true'){
        		 $('#payBackFeeDate').datebox({ disabled: false});
        		          }
        	else
        		{
        		 $('#payBackFeeDate').datebox({ disabled: true});
        		}
           
        });
        
    })
	
	function addCell(){
		// $("#addcelltable tr:eq(2)").after("<tr><td><input id=\"sd\" type=\"checkbox\" /></td><td><input type=\"text\" /></td></tr>");
      $("#addcelltable tr:eq(5)").after("<tr><td>货物：</td><td><input id=\"cc\" name=\"exportGoods.goodsid\"  style=\"width:80px\" /> <input id=\"exportprovider\" name=\"exportGoods.providerid\" required=\"true\" style=\"width:80px\" /> <input id=\"warehouse\" name=\"exportGoods.warehouseid\" style=\"width:50px\"/> </td><td>剩余：</td><td><input type=\"text\" id=\"storenum\" name=\"storeNum\" style=\"width:40px\" readonly=\"true\"/>提出：<input  id=\"cnum\" name=\"exportGoods.goodsnum\" style=\"width:40px\"/><input type='button' value='删除' onclick='delCell(this)'/></td></tr>");
      $('#cc').combobox({
  		url: '${pageContext.request.contextPath}/stockManageSystem/goodsType!goodsTypeComboList',
  		required: true,
  		valueField: 'id',
  		textField: 'typeName'
  	});
      
      $('#warehouse').combobox({
    		url: '${pageContext.request.contextPath}/stockManageSystem/warehouse!warehouseComboList',
    		required: true,
    		valueField: 'hName',
    		textField: 'hName'
    	});
	
     
     $('#exportprovider').combobox({
    		url: '${pageContext.request.contextPath}/stockManageSystem/provider!providerComboList',
    		required: true,
    		valueField: 'id',
    		textField: 'proName',
    		onSelect: function (provi) {
    			//TO DO
    			if ($('#cc').combobox('getValue')) {
					
				}   
    			var goods = $('#cc').combobox('getValue');
    			var warehouse = $('#warehouse').combobox('getValue');
    			var provider = provi.id;
    			$.post("${pageContext.request.contextPath}/stockManageSystem/export!storeResearch",{providerID:provider,goodsID:goods,wareHouse:warehouse },
    					function(result){
					if(result){
						$('#storenum').val(result.storageNums);
					}else{
						$.messager.alert('系统提示',result.errorMsg);
						$('#storenum').val('');
					}
				},"json");
    		 }
    	});
	
      $('#cnum').validatebox({
    		required: true,
    		validType: 'number'
    	});
	}
	
	function delCell(inputobj){
		    if(inputobj==null) return;  
		    var parentTD = inputobj.parentNode;  //parentNode是父标签的意思，如果你的TD里用了很多div控制格式，要多调用几次parentNode
		    var parentTR = parentTD.parentNode;  
		    var parentTBODY = parentTR.parentNode;  
		    parentTBODY.removeChild(parentTR);  
	}
	
	function openChoiceGoodsDialog(){
		$("#dlg2").dialog("open").dialog("setTitle","选择商品");
	}
	
	function searchExport2(){
		$('#dg2').datagrid('load',{
			s_goodsName:$('#s_goodsName').val(),
		});
	}
	
	function deleteExport(){
		var selectedRows = $("#dg1").datagrid("getSelections");
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].id);
		}
		var ids = strIds.join(",");
		$.messager.confirm("系统提示","您确认要删除这<font color=red>"+selectedRows.length+"</font>条数据吗?",function(r){
			if(r){
				$.post("${pageContext.request.contextPath}/stockManageSystem/export!delete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
						$("#dg1").datagrid("reload");
					}else{
						$.messager.alert('系统提示',result.errorMsg);
					}
				},"json");
			}
		});
	}
	
	function openExportAddDialog(){
		$("#dlg1").dialog("open").dialog("setTitle","添加运输信息");
		url="${pageContext.request.contextPath}/stockManageSystem/export!save";
	}
	
	function closeExportDialog(){
		$("#dlg1").dialog("close");
		resetValue();
	}
	
	function resetValue(){
		$("#s_providerName").combobox("setValue","");
		
		$("#expoDate").datebox("setValue","");
		$("#expoNum").val("");
		$("#expoDesc").val("");
	}
	
	function saveExport(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				if(result.errorMsg){
					$.messager.alert("系统提示",reuslt.errorMsg);
					return error;
				}else{
					$.messager.alert("系统提示","保存成功");
					//resetValue();
					$("#dlg1").dialog("close");
					$("#dg1").datagrid("reload");
				}
			}
		});
	}
	
	function openExportModifyDialog(){
		var selectedRows = $("#dg1").datagrid("getSelections");
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要修改的数据");
			return ;
		}
		var row = selectedRows[0];
		$("#dlg1").dialog("open").dialog("setTitle","编辑出库信息");
		$("#driver").combobox("setValue",row.driver);
		$("#carNum").val(row.carNum);
		$("#fromPlace").val(row.fromPlace);
		$("#toPlace").val(row.toPlace);
		$("#isCheckOwner").val(row.isCheckOwner);
		$("#carDes").val(row.carDes);
		$("#phoneNum").val(row.phoneNum);
		$("#shipper").val(row.shipper);
		$("#payDate").datebox("setValue",row.payDate);
		$("#payBackFeeDate").datebox("setValue",row.payBackFeeDate);
		$("#expoDate").datebox("setValue",row.expoDate);
		$("#contactNum").val(row.contactNum);
		$("#contactDesc").val(row.contactDesc);
		$("#payDesc").val(row.payDesc);
		$("#expoDesc").val(row.expoDesc);
		url="${pageContext.request.contextPath}/stockManageSystem/export!save?id="+row.id;
	}
	
	function cleraValue(){
		
		$("#s_eexpoPrice").val("");
		$("#s_bexpoDate").datebox("setValue","");
		$("#s_eexpoDate").datebox("setValue","");
	}
	
	function exportData(){
		window.open('${pageContext.request.contextPath}/stockManageSystem/export!export')
	}
</script>
</head>
<body style="margin: 5px;">
	<table style="height:423px; width:1160px" id="dg1" title="运输清单" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="${pageContext.request.contextPath}/stockManageSystem/export" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="10">货运编号</th>
				<th field=expoDate width="15">出库日期</th>
				<th field="fromPlace" width="10">出发地</th>
				<th field="toPlace" width="10">目的地</th>
				<th field="driver" width="10">司机</th>
				<th field="carNum" width="20">车号</th>
				<th field="phoneNum" width="20">手机号</th>
				<th field="payDate" width="15">运费付款日期</th>
				<th field="shipper" width="15">装货人</th>
				<th field="contactNum" width="20">合同号</th>
				<th field="expoDesc" width="50">出库备注</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			<a href="javascript:openExportAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a href="javascript:openExportModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			<a href="javascript:deleteExport()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="exportData()">导出数据</a>
		</div>
		<div>
		&nbsp;&nbsp;出库目标客户：&nbsp;<input type="text" name="s_providerName" id="s_providerName"  size="10"/>
		&nbsp;出库时间：&nbsp;<input class="easyui-datebox" name="s_bexpoDate" id="s_bexpoDate" editable="false" size="10"/>-><input class="easyui-datebox" name="s_eexpoDate" id="s_eexpoDate" editable="false" size="10"/> 
		
		&nbsp;&nbsp;&nbsp;<a href="javascript:searchExport1()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
		&nbsp;&nbsp;&nbsp;<a href="javascript:openChoiceGoodsDialog()" class="easyui-linkbutton" iconCls="icon-tip">选择商品</a>
		<a href="javascript:cleraValue()" class="easyui-linkbutton" iconCls="icon-no" plain="true">清空</a>
		</div>
		
	</div>
	
	<div id="dlg1" class="easyui-dialog" style="width:680px;height:400px;padding: 10px 20px"
	closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table  id ="addcelltable"  cellspacing="5px;">
			
			<tr>
					<td>出发地：</td>
					<td><input class="easyui-validatebox" id="fromPlace" name="exportGoods.fromPlace" required="false" /></td>
					<td>目的地：</td>
					<td><input  name="exportGoods.toPlace" id="toPlace" class="easyui-validatebox" required="false" /></td>
				</tr>
				<tr>
					<td>司机：</td>
					<td><input class="easyui-combobox" id="driver" name="exportGoods.driver"  data-options="panelHeight:'auto',editable:true,valueField:'name',textField:'name',url:'${pageContext.request.contextPath}/stockManageSystem/drivers!dirversComboList'" onselect=""/></td>
					
					<td>车号：</td>
					<td><input  name="exportGoods.carNum" id="carNum" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<td>车备注：</td>
					<td><input class="easyui-text" id="carDes" name="exportGoods.carDes" /></td>
					
					<td>是否核实货主：</td>
					<td><input  name="exportGoods.isCheckOwner" id="isCheckOwner" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<td>手机号：</td><td><input  name="exportGoods.phoneNum" id="phoneNum" class="easyui-validatebox" required="true" /></td>
					
					
					<td>装货人：</td>
					<td><input  name="exportGoods.shipper" id="shipper" class="easyui-validatebox" required="true" /></td>
					
				</tr>
				<tr>
					<td>是否付运费：</td>
					<td><input type="radio" name="ischarge" value="true" checked="checked">是</input>
                        <input type="radio" name="ischarge" value="false" >否</input></td>
					
					<td>运费付款日期：</td>
					<td><input  name="exportGoods.payDate" id="payDate" class="easyui-datebox" required="true"/></td>
				</tr>
				<tr>
					<td>是否付回单钱：</td>
					<td><input type="radio" name="isbackbill" value="true" checked="checked">是</input>
                        <input type="radio" name="isbackbill" value="false" >否</input></td>
					
					<td>回单付款日期：</td>
					<td><input  name="exportGoods.payBackFeeDate" id="payBackFeeDate" class="easyui-datebox" required="true" /></td>
				</tr>
				
				
				<tr>
				    <td>
				   
		<a href="javascript:addCell()" class="easyui-linkbutton" iconCls="icon-add">增加货物</a>
	                
				    </td> 
				</tr>
				
				
				
				<tr id="addflag">
					<td>出库日期：</td>
					<td><input class="easyui-datebox" name="exportGoods.expoDate" id="expoDate" />
					<td>运费备注：</td>
					<td><input type="text" name="exportGoods.payDesc" id="payDesc"/></td>
				</tr>
				
				<tr>
					<td>合同号：</td>
					<td><input class="text" name="exportGoods.contactNum" id="contactNum"/> </td>
					<td>合同备注：</td>
					<td><input type="text" name="exportGoods.contactDesc" id="contactDesc" /></td>
				</tr>
				
				<tr>
					<td valign="top">出库备注：</td>
					<td colspan="4"><textarea rows="7" cols="43" name="exportGoods.expoDesc" id="expoDesc"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveExport()" class="easyui-linkbutton" iconCls="icon-save">保存</a>
		<a href="javascript:closeExportDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
	
	<!-- 选择商品 -->
	<div id="dlg2" class="easyui-dialog" style="width: 600px;height: 350px;padding: 10px 20px"
		closed="true">
		
			<table cellspacing="5px;">
				<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;客户名称：</td>
					<td><input type="text" size="15" name="s_providerName" id="s_providerName" class="easyui-validatebox" /></td>
					<td><a href="javascript:searchExport2()" class="easyui-linkbutton" iconCls="icon-search">搜索</a></td>
				</tr>
				<tr>
					<td colspan="3">
						<table style="height:250px; width:540px" id="dg2" title="商品选择" class="easyui-datagrid" fitColumns="true"
	 							pagination="true" rownumbers="true" url="${pageContext.request.contextPath}/stockManageSystem/export">
	 							<thead>
									<tr>
									
										<th field="goodsId" width="20" hidden="true">商品ID</th>
										<th field="s_providerName" width="70">客户名称</th>
										<th field="expoPrice" width="70">销售价格</th>
										<th field="expoDate" width="70">出库时间</th>
										<th field="expoNum" width="70">出库数量</th>
										<th field="expoDesc" width="100">出库备注</th>
									</tr>
								</thead>
	 							
	 					</table>
					</td>
				</tr>
			</table>
		
	</div>
</body>
</html>