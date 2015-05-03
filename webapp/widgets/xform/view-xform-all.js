/*
 * Compressed by JSA(www.xidea.org)
 */
var xf_f_name_prefix='xf_f_';
var xf_f_flow_view;   //流程查看
var UserSelection_new_inner_html='';

xf = {
};

xf.field = {
};

xf.$ = function(id) {
	return document.getElementById(id);
}

xf.addClass = function(el, className) {
	if ((' ' + el.className).indexOf(' ' + className) == -1) {
		el.className += ' ' + className;
	}
}

xf.removeClass = function(el, className) {
	var cls = el.className;
	while (cls.indexOf(' ' + className) != -1) {
		var index = cls.indexOf(' ' + className);
		cls = cls.substring(0, index) + cls.substring(index + className.length + 1);
	}
	while (cls.indexOf(className + ' ') != -1) {
		var index = cls.indexOf(className + ' ');
		cls = cls.substring(0, index) + cls.substring(index + className.length + 1);
	}
	el.className = cls;
}

xf.getTarget = function(e) {
	var ev = window.event ? window.event : e;
	var x = ev.clientX;
	var y = ev.clientY;
	var target = ev.srcElement ? ev.srcElement : ev.target;
	return target;
}

xf.getHandler = function(el) {
	while (el) {
		if (el.className && el.className == 'xf-handler') {
			return el;
		}
		el = el.parentNode;
	}
	return null;
}

xf.getPosition = function(e) {
	var ev = window.event ? window.event : e;
	var x = ev.clientX;
	var y = ev.clientY;
	return {
		x: x,
		y: y
	};
}

xf.insertAfter = function(newElement, targetElement) {
    var parent = targetElement.parentNode;
    if(parent.lastChild == targetElement) {
        parent.appendChild(newElement);
    }else {
        parent.insertBefore(newElement, targetElement.nextSibling);
    }
}

xf.createField = function(label, value, callback, self, formNode) {

	var labelNode = document.createElement('label');
	labelNode.innerHTML = label + ':';
	labelNode.className = 'control-label';

	var input = document.createElement('input');
	input.type = 'text';
	input.value = value;
	input.onblur = function() {
		if(label==xf.field.attrName()){
			//cannot contain invalid chars
			if(/[@#\$%\^&\*]+/g.test(this.value)){
				jQuery.messager.show({
					title:'提示！',
					msg:'name属性不能含有[@#$%^&*\\]等特殊字符！',
					showType:'slide',
					style:{
						right:'',
						top:document.body.scrollTop+document.documentElement.scrollTop,
						bottom:''
					}
				});
				this.value = value;
				this.focus();
				return false;
			}
			if(jQuery.trim(this.value).length==0){
				jQuery.messager.show({
					title:'提示！',
					msg:'name属性不能为空！',
					showType:'slide',
					style:{
						right:'',
						top:document.body.scrollTop+document.documentElement.scrollTop,
						bottom:''
					}
				});
				this.value = value;
				this.focus();
			}
			try{
				for (var i = 0; i < xform.sections.length; i++) {
					var section = xform.sections[i];
					if(section.fieldMap){
						var duplicate = false;
						for (var key in section.fieldMap) {
							 var field = section.fieldMap[key];
							 if(self.parentId==key){continue;}
							 if(this.value==field.name){
								 duplicate=true;
								 break;
							 }
						}
						//console.log(this.value+':'+duplicate);
						if (duplicate) {
							jQuery.messager.show({
								title:'提示！',
								msg:'name属性"'+this.value+'"重复！',
								showType:'slide',
								style:{
									right:'',
									top:document.body.scrollTop+document.documentElement.scrollTop,
									bottom:''
								}
							});
							this.value = value;
							this.focus();
							return false;
						}
						//field.name=this.value;
						section.fieldMap[self.parentId].name=this.value;
					}
				}
			}catch(e){
				console.log(e);
			}
		}
		callback.call(self, this.value);
	}

	formNode.appendChild(labelNode);
	formNode.appendChild(input);
}

xf.createSelect = function(label, value, callback, self, formNode) {

	var labelNode = document.createElement('label');
	labelNode.innerHTML = label + ':';
	labelNode.className = 'control-label';
	
	var select = document.createElement('select');
	select.style.width='150px';
	select.onchange=function(){
		callback.call(self, this.value,formNode);
	}
	
	try{
		select.options[0] = new Option('请选择','');
		if(selectionData && selectionData.length>0){
			for(var i=0;i<selectionData.length;i++)
		    {
				var nameValPair = selectionData[i];
				select.options[i+1] = new Option(nameValPair.name,nameValPair.value);
				if(nameValPair.value==value){
					select.options[i+1].selected=true;
				}
		    }
		}
	}catch(e){
		console.log(e);
	}

	formNode.appendChild(labelNode);
	formNode.appendChild(select);
}

xf.createBooleanField = function(label, value, callback, self, formNode) {
	var labelNode = document.createElement('label');
	labelNode.className = 'checkbox control-group';

	var input = document.createElement('input');
	input.type = 'checkbox';
	input.value = 'true';
	if (value) {
		input.checked = true;
	}
	input.onclick = function() {
		callback.call(self, this.checked);
	}
	input.style.marginLeft = '0px';

	labelNode.appendChild(input);
	labelNode.appendChild(document.createTextNode(' ' + label));

	formNode.appendChild(labelNode);
}

;

xf.Xform = function(id) {
	this.id = id;

	// 默认模式是拖拽编辑添加组件
	// 可以切换成合并单元格的模式
	this.mode = 'EDIT';

	this.sections = [];

	this.sed = 0;
	this.proxy = new xf.Proxy();
	this.fieldFactory = new xf.field.FieldFactory();
}

xf.Xform.prototype.addSection = function(section) {
	section.id = this.getId();
	section.xform = this;
	this.sections.push(section);
}

xf.Xform.prototype.initEvents = function() {
	var self = this;
	document.onmousedown = function(e) {
		self.mouseDown(e);
	}
	document.onmousemove = function(e) {
		self.mouseMove(e);
	}
	document.onmouseup = function(e) {
		self.mouseUp(e);
	}
	document.onmouseover = function(e){
		//self.mouseOver(e);
	}
	document.onmouseout = function(e){
		//self.mouseOut(e);
	}
}

xf.Xform.prototype.mouseDown = function(e) {
	var target = xf.getTarget(e);
	var handler = xf.getHandler(target);
	if (handler) {
		e.preventDefault();
	}

	if (this.mode == 'EDIT') {
		if (target.className == 'xf-pallete') {
			this.request = {
				type: 'add',
				fieldType: target.title
			}
		} else if (handler) {
			var section = this.findSection(e);
			if (section) {
				var field = section.findField(target);
				if (field) {
					this.request = {
						type: 'move',
						section: section,
						field: field
					};
				}
			}
		}
	} else if (this.mode == 'MERGE') {
		var section = this.findSection(e);
		if (section) {
			section.mergeStart(e);
			this.request = {
				status: 'merge'
			};
		}
	}

	var section = this.findSection(e);
	if (section) {
		section.selectSomething(e);
	}
}

xf.Xform.prototype.mouseMove = function(e) {
	if (!this.request) {
		return;
	}

	if (this.mode == 'EDIT') {
		var position = xf.getPosition(e);
		this.proxy.move(position.x + 5, position.y + 5);
	} else if (this.mode == 'MERGE') {
		var section = this.findSection(e);
		if (section) {
			section.mergeMove(e);
		}
	}
}

xf.Xform.prototype.mouseUp = function(e) {
	if (!this.request) {
		return;
	}

	if (this.mode == 'EDIT') {
		this.proxy.hide();

		var target = xf.getTarget(e);
		var section = this.findSection(e);
		if (section) {
			if (this.request.type == 'add') {
				section.addField(this.request, target);
			} else if (this.request.type == 'move') {
				section.moveTo(this.request.field, target);
			}
		}
		this.request = null;
	} else if (this.mode == 'MERGE') {
		var section = this.findSection(e);
		if (section) {
			section.mergeEnd(e);
		}
		this.request = null;
	}
}

xf.Xform.prototype.mouseOver = function(e) {
	if (this.mode == 'EDIT') {
		var section = this.findSection(e);
		if (section) {
			section.mouseOver(e);
		}
	}
}

xf.Xform.prototype.mouseOut  = function(e) {
	if (this.mode == 'EDIT') {
		var section = this.findSection(e);
		if (section) {
			section.mouseOut(e);
		}
	}
}

xf.Xform.prototype.findSection = function(e) {
	var target = xf.getTarget(e);
	var parent = target;
	while (true) {
		if (parent.className != null && parent.className.indexOf('xf-section') != -1) {
			for (var i = 0; i < this.sections.length; i++) {
				var section = this.sections[i];
				if (section.id == parent.id) {
					return section;
				}
			}
		}

		parent = parent.parentNode;
		if (!parent) {
			return null;
		}
	}
}

xf.Xform.prototype.getId = function() {
	return 'xf-g-' + (this.sed++);
}

xf.Xform.prototype.addRow = function() {
	var section = this.sections[1];
	section.addRow();
}

xf.Xform.prototype.addColumn = function() {
	var section = this.sections[1];
	section.addColumn();
}

xf.Xform.prototype.render = function() {
	for (var i = 0; i < this.sections.length; i++) {
		var section = this.sections[i];
		section.render();
	}
}

xf.Xform.prototype.doExport = function() {
	var text = '{"sections":[';
	for (var i = 0; i < this.sections.length; i++) {
		var sectionText = this.sections[i].doExport();
		text += sectionText;
		if (i != this.sections.length - 1) {
			text += ',';
		}
	}
	text += ']}';
	return text;
}
//arguments[0:,1:'flow',2:'input-form-template']
xf.Xform.prototype.doImport = function(text) {
	var o = eval('(' + text + ')');

	xf.$(this.id).innerHTML = '';
	this.sections = [];

	for (var i = 0; i < o.sections.length; i++) {
		var sectionData = o.sections[i];
		switch (sectionData.type) {
		case 'text':
			if(arguments[1]!='flow'){
				var section = new xf.TextSection(sectionData.tag, sectionData.text);
				xform.addSection(section);
			}
			break;
		case 'grid':
			var section = new xf.GridSection(sectionData.row, sectionData.col);
			xform.addSection(section);
			if(arguments.length>2){
				section.doImport(sectionData,arguments[1],arguments[2]);
			}else{
				section.doImport(sectionData);
			}
			break;
		}
	}

	this.render();
}

xf.Xform.prototype.doMerge = function() {
	for (var i = 0; i < this.sections.length; i++) {
		this.sections[i].merge();
	}
	//重新点击时，去除选中cell active样式
	this.doCleanSelectedItem();
}

xf.Xform.prototype.doCleanSelectedItem = function() {
	console.log('doCleanSelectedItem');
	for (var i = 0; i < this.sections.length; i++) {
		this.sections[i].doCleanSelectedItem();
	}
}

xf.Xform.prototype.doSplit = function() {
	for (var i = 0; i < this.sections.length; i++) {
		this.sections[i].split();
	}
}

xf.Xform.prototype.setValue = function(data) {
	for (var i = 0; i < this.sections.length; i++) {
		var section = this.sections[i];
		section.setValue(data);
	}
}

;

xf.TextSection = function(tag, text) {
	this.tag = tag;
	this.text = text;
}

xf.TextSection.prototype.render = function() {	
	var el = document.createElement('div');
	el.id = this.id;
	el.className = 'xf-section';
	el.innerHTML = '<' + this.tag + ' style="text-align:center;">'
		+ this.text
		+ '</' + this.tag + '>';

	var xformEl = xf.$(this.xform.id);

	xformEl.appendChild(el);
}

xf.TextSection.prototype.addField = function(request, target) {
}

xf.TextSection.prototype.doExport = function() {
	return '{"type":"text","tag":"' + this.tag + '","text":"' + this.text + '"}';
}

xf.TextSection.prototype.selectSomething = function(e) {
	this.xform.selectionListener.select(this);
}

xf.TextSection.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrText(), this.text, this.updateText, this, formNode);
}

xf.TextSection.prototype.updateText = function(text) {
	this.text = text;
	var el = xf.$(this.id);
	el.innerHTML = '<' + this.tag + ' style="text-align:center;">'
		+ this.text
		+ '</' + this.tag + '>';
}

xf.TextSection.prototype.mergeStart = function(e) {
}

xf.TextSection.prototype.mergeMove = function(e) {
}

xf.TextSection.prototype.mergeEnd = function(e) {
}

xf.TextSection.prototype.mouseOver = function(e) {
}

xf.TextSection.prototype.mouseOut = function(e) {
}

xf.TextSection.prototype.merge = function() {
}

xf.TextSection.prototype.doCleanSelectedItem = function() {
}

xf.TextSection.prototype.split = function() {
}

xf.TextSection.prototype.setValue = function(data) {
}

;

xf.GridSection = function(row, col) {
	this.row = row;
	this.col = col;
	this.fieldMap = {};
	this.mergeMap = {};

	this.selectedItems = [];
	this.startCell = [];
	this.endCell = [];
}

xf.GridSection.prototype.findCell = function(el) {
	while (el) {
		if (el.className && el.className.indexOf('xf-cell') != -1) {
			return el;
		}
		el = el.parentNode;
	}
	return false;
}

xf.GridSection.prototype.merge = function() {
	if (this.selectedItems.length == 0) {
		return false;
	}
	var minRow = this.minRow;
	var minCol = this.minCol;
	var maxRow = this.maxRow;
	var maxCol = this.maxCol;

	var els = this.selectedItems;
	for (var i = 0; i < els.length; i++) {
		var el = els[i];
		var position = this.getPosition(el);
		
		var minRowTmp = position.row;
		var minColTmp = position.col;
		var maxRowTmp = position.row + (position.height-1);
		var maxColTmp = position.col + (position.width-1);
			
		//minRow
		if(minRow>minRowTmp){minRow=minRowTmp;}
		//maxRow
		if(maxRow<maxRowTmp){maxRow=maxRowTmp;}
		//minCol
		if(minCol>minColTmp){minCol=minColTmp;}
		//maxCol
		if(maxCol<maxColTmp){maxCol=maxColTmp;}
	}
	//console.log('merge--->:minRow:'+minRow+',maxRow:'+maxRow+',minCol:'+minCol+',maxCol:'+maxCol);
	var startId = this.id + '-' + minRow + '-' + minCol;
	var el = xf.$(startId);
	if (!el) {
		return false;
	}
	
	var colSpan = 1 + maxCol - minCol;
	var rowSpan = 1 + maxRow - minRow;
	el.setAttribute("colspan", colSpan);
	el.setAttribute("rowspan", rowSpan);
	//var els = this.selectedItems.slice(1);
	/**for (var i = 0; i < els.length; i++) {
		var elTmp = els[i];
		if(elTmp.id!=el.id){
			elTmp.parentNode.removeChild(elTmp);
			console.log('delete:'+elTmp.id);
		}
	}**/
	
	for (var i = minRow; i < (maxRow+1); i++) {
		for (var j = minCol; j < (maxCol+1); j++) {
			var id = this.id + '-' + i + '-' + j;
			var elTmp = xf.$(id);
			if (elTmp && elTmp.id!=el.id) {
				elTmp.parentNode.removeChild(elTmp);
			}
		}
	}
	
	//删除 mergeMap 中重复的合并属性
	for(var key in this.mergeMap){
		var mergeObj = this.mergeMap[key];
		var mergeMinRow = mergeObj.minRow;
		var mergeMinCol = mergeObj.minCol;
		var mergeMaxRow = mergeObj.maxRow;
		var mergeMaxCol = mergeObj.maxCol;
		if(minRow<=mergeMinRow && minCol<=mergeMinCol &&
				maxRow>=mergeMaxRow && maxCol>=mergeMaxCol){
			delete this.mergeMap[key];
		}
	}

	this.mergeMap[minRow + '-' + minCol] = {
		minRow: minRow,
		minCol: minCol,
		maxRow: maxRow,
		maxCol: maxCol
	};

	this.selectedItems = [];
	var tds = document.getElementsByTagName('TD');
	for (var i = 0; i < tds.length; i++) {
		xf.removeClass(tds[i], 'active');
	}
}

xf.GridSection.prototype.doCleanSelectedItem = function() {
	var tds = document.getElementsByTagName('TD');
	for (var i = 0; i < tds.length; i++) {
		xf.removeClass(tds[i], 'active');
	}
	this.selectedItems = [];
}

xf.GridSection.prototype.split = function() {
	if (this.selectedItems.length == 0) {
		return false;
	}
	var el = this.selectedItems[0];
	var position = this.getPosition(el);
	var minRow = position.row;
	var minCol = position.col;

	if (position.width == 1 && position.height == 1) {
		return false;
	}
	el.setAttribute("colspan", 1);
	el.setAttribute("rowspan", 1);

	for (var i = 0; i < position.width; i++) {

		for (var j = 0; j < position.height; j++) {
			if (i == 0 && j == 0) {
				continue;
			}
			var targetRow = position.row + j;
			var targetCol = position.col + i;

			var td = document.createElement('td');
			td.innerHTML = '<div>&nbsp;</div>';
			td.id = this.id + '-' + targetRow + '-' + targetCol;

			td.className = 'xf-cell xf-cell-right xf-cell-bottom';
			if (targetCol == 0) {
				td.className += ' xf-cell-left';
			}
			if (targetRow == 0) {
				td.className += ' xf-cell-top';
			}

			var preTd = xf.$(this.id + '-' + targetRow + '-' + (targetCol - 1));

			if (preTd == null) {
				var tr = xf.$(this.id + '-' + targetRow);
				if (tr.children.length == 0) {
					tr.appendChild(td);
				} else {
					tr.insertBefore(td, tr.firstChild);
				}
			} else {
				xf.insertAfter(td, preTd);
			}
		}
	}

	delete this.mergeMap[minRow + '-' + minCol];

	this.selectedItems = [];
	var tds = document.getElementsByTagName('TD');
	for (var i = 0; i < tds.length; i++) {
		xf.removeClass(tds[i], 'active');
	}
}

xf.GridSection.prototype.render = function() {
	// table
	var el = document.createElement('div');
	el.id = this.id;
	el.className = 'xf-section';

	var html = '<table class="xf-table" cellspacing="0" cellpadding="5" width="100%" align="center" style="border-collapse: collapse;font-size: 12px;">'
		+ '<tbody>';

	for (var i = 0; i < this.row; i++) {
		var rowId = this.id + '-' + i;

		html += '<tr id="' + rowId + '">'
		for (var j = 0; j < this.col; j++) {
			var cellId = rowId + '-' + j;
			var cellClassName = 'xf-cell-right xf-cell-bottom';
			if (i == 0) {
				cellClassName += ' xf-cell-top';
			}
			if (j == 0) {
				cellClassName += ' xf-cell-left';
			}
			html += '<td id="' + cellId + '" class="' + cellClassName + '" width="' + (100 / this.col) + '%">&nbsp;'
				+ '</td>';
		}
		html += '</tr>';
	}

	html += '</tbody>'
		+ '</table>';

	el.innerHTML = html;

	var xformEl = xf.$(this.xform.id);
	xformEl.appendChild(el);

	// field
	for (var key in this.fieldMap) {
		var field = this.fieldMap[key];
		field.render();
	}

	// merge
	for (var key in this.mergeMap) {
		var info = this.mergeMap[key];
		this.minRow = info.minRow;
		this.minCol = info.minCol;
		this.maxRow = info.maxRow;
		this.maxCol = info.maxCol;
		for (var i = this.minRow; i <= this.maxRow; i++) {
			for (var j = this.minCol; j <= this.maxCol; j++) {
				var node = xf.$(this.id + '-' + i + '-' + j);
				this.selectedItems.push(node);
			}
		}
		this.merge();
	}
}

xf.GridSection.prototype.addField = function(request, target) {
	var el = this.findCell(target);
	if (el) {
		var fieldFactory = this.xform.fieldFactory;
		var field = fieldFactory.create(request.fieldType, el);
		field.render();
		this.fieldMap[el.id] = field;
	}
}

xf.GridSection.prototype.doExport = function() {
	var text = '{"type":"grid","row":"' + this.row + '","col":"' + this.col + '","merge":[';

	var mergeExists = false;
	for (var key in this.mergeMap) {
		mergeExists = true;

		var startId = key;
		var mergeInfo = this.mergeMap[startId];
		text += '{"startId":"' + startId
			+ '","minRow":' + mergeInfo.minRow
			+ ',"minCol":' + mergeInfo.minCol
			+ ',"maxRow":' + mergeInfo.maxRow
			+ ',"maxCol":' + mergeInfo.maxCol
			+ '},';
	}
	if (mergeExists) {
		text = text.substring(0, text.length - 1);
	}

	var fieldExists = false;
	text += '],"fields":[';
	for (var key in this.fieldMap) {
		fieldExists = true;

		var fieldId = key;
		var fieldValue = this.fieldMap[fieldId];
		text += fieldValue.doExport() + ',';
	}
	if (fieldExists) {
		text = text.substring(0, text.length - 1);
	}

	text += ']}';
	return text;
}

xf.GridSection.prototype.addRow = function() {
	var tbody = this.findTbody();
	var tr = document.createElement('tr');
	tr.id = this.id + '-' + this.row;
	tbody.appendChild(tr);
	for (var i = 0; i < this.col; i++) {
		var td = document.createElement('td');
		td.id = tr.id + '-' + i;
		td.className = 'xf-cell-right xf-cell-bottom';
		if (i == 0) {
			td.className += ' xf-cell-left';
		}
		td.width = (100 / this.col) + '%';
		td.innerHTML = '&nbsp;';
		tr.appendChild(td);
	}
	this.row++;
}

xf.GridSection.prototype.addColumn = function() {
	
	var tbody = this.findTbody();
	if(!tbody){
		return false;
	}
	
	this.col++;
	
	for (var i = 0; i < tbody.childNodes.length; i++) {
		var tr = tbody.childNodes[i];
		
		var lastTd = tr.childNodes[tr.childNodes.length-1];
		var array = lastTd.id.split('-');
		var col = parseInt(array[4]);
		
		var td = document.createElement('td');
		td.id = tr.id + '-' + (col+1);
		td.className = 'xf-cell-right xf-cell-top xf-cell-bottom';
		console.log('width:'+lastTd.width);
		td.width = lastTd.width;
		td.innerHTML = '&nbsp;';
		tr.appendChild(td);
	}
}

xf.GridSection.prototype.findTbody = function() {
	var el = xf.$(this.id);

	for (var i = 0; i < el.childNodes.length; i++) {
		var childNode = el.childNodes[i];
		if (childNode.tagName == 'TABLE') {
			el = childNode;
		}
	}

	for (var i = 0; i < el.childNodes.length; i++) {
		var childNode = el.childNodes[i];
		if (childNode.tagName == 'TBODY') {
			el = childNode;
		}
	}
	return el;
}

xf.GridSection.prototype.selectSomething = function(e) {
	var target = xf.getTarget(e);
	var cell = this.findCell(target);
	var field = this.fieldMap[cell.id];
	this.xform.selectionListener.select(field);
}

xf.GridSection.prototype.doImport = function(sectionData) {
	this.fieldMap = {};
	
	for (var i = 0; i < sectionData.fields.length; i++) {
		var fieldData = sectionData.fields[i];
		
		var field = this.xform.fieldFactory.create(fieldData.type);
		field.parentId = this.id + '-' + fieldData.row + '-' + fieldData.col;
		for (var key in fieldData) {
			if(arguments.length>2 && arguments[2]=='input-form-template'){
				if(key=='name' && fieldData[key].indexOf(xf_f_name_prefix)>=0){
					field[key] = fieldData[key].substring(xf_f_name_prefix.length);
					continue;
				}
			}
			
			field[key] = fieldData[key];
		}
		this.fieldMap[field.parentId] = field;
	}

	this.mergeMap = {};
	if (sectionData.merge) {
		for (var i = 0; i < sectionData.merge.length; i++) {
			var mergeData = sectionData.merge[i];
			this.mergeMap[mergeData.startId] = mergeData;
		}
	}
}

xf.GridSection.prototype.getPosition = function(el) {
	if (!el || !el.id) {
		return false;
	}
	var array = el.id.split('-');
	var p = {};
	p.row = parseInt(array[3]);
	p.col = parseInt(array[4]);
	p.width = parseInt(el.colSpan);
	if (p.width == 0) {
		p.width = 1;
	}
	p.height = parseInt(el.rowSpan);
	if (p.height == 0) {
		p.height = 1;
	}
	return p;
}

xf.GridSection.prototype.mergeStart = function(e) {
	//重新点击时，去除选中cell active样式
	this.doCleanSelectedItem();
	
	var target = xf.getTarget(e);
	var cell = this.findCell(target);

	if (!cell) {
		return;
	}
	//console.log('mergeStart:'+cell.id);
	for (var i = 0; i < this.selectedItems.length; i++) {
		xf.removeClass(this.selectedItems[i], 'active');
	}
	xf.addClass(cell, 'active');
	
	this.status = 'DRAG';
	this.startCell = cell;
	this.selectedItems.push(cell);
}

xf.GridSection.prototype.mergeMove = function(e) {

	if (this.status == 'DRAG') {
		var target = xf.getTarget(e);
		var cell = this.findCell(target);

		if (!cell) {
			return;
		}
		//console.log('mergeMove:'+cell.id);
		
		this.selectedItems.push(cell);
		
		var endCell = cell;
		var startCell = this.startCell;
		
		var startPosition = this.getPosition(startCell);
		var endPosition = this.getPosition(endCell);

		if (startCell.id == endCell.id) {
			return;
		}
		
		var minRow = Math.min(startPosition.row, endPosition.row);
		var minCol = Math.min(startPosition.col, endPosition.col);
		var maxRow = Math.max(startPosition.row+(startPosition.height-1), endPosition.row+(endPosition.height-1));
		var maxCol = Math.max(startPosition.col+(startPosition.width-1), endPosition.col+(endPosition.width-1));
		//console.log('1:minRow:'+minRow+',maxRow:'+maxRow+',minCol:'+minCol+',maxCol:'+maxCol);
		var els = this.selectedItems;
		for (var i = 0; i < els.length; i++) {
			var el = els[i];
			var position = this.getPosition(el);
			
			var minRowTmp = position.row;
			var minColTmp = position.col;
			var maxRowTmp = position.row + (position.height-1);
			var maxColTmp = position.col + (position.width-1);
				
			//minRow
			if(minRow>minRowTmp){minRow=minRowTmp;}
			//maxRow
			if(maxRow<maxRowTmp){maxRow=maxRowTmp;}
			//minCol
			if(minCol>minColTmp){minCol=minColTmp;}
			//maxCol
			if(maxCol<maxColTmp){maxCol=maxColTmp;}
		}
		
		//console.log('2:minRow:'+minRow+',maxRow:'+maxRow+',minCol:'+minCol+',maxCol:'+maxCol);

		this.selectedItems = [];
		this.minRow = minRow;
		this.minCol = minCol;
		this.maxRow = maxRow;
		this.maxCol = maxCol;
		for (var i = minRow; i <= maxRow; i++) {
			for (var j = minCol; j <= maxCol; j++) {
				var el = xf.$(this.id + '-' + i + '-' + j);
				if(el){
					xf.addClass(el, 'active');
					this.selectedItems.push(el);
				}
			}
		}

	}
}

xf.GridSection.prototype.mergeEnd = function(e) {
	//console.log('mergeEnd');
	if (this.status == 'DRAG') {
		this.status = 'DROP';
	}
}

xf.GridSection.prototype.mouseOver = function(e) {
	var target = xf.getTarget(e);
	var cell = this.findCell(target);
	if (!cell) {
		return;
	}
	xf.addClass(cell, 'over');
}

xf.GridSection.prototype.mouseOut = function(e) {
	var target = xf.getTarget(e);
	var cell = this.findCell(target);
	if (!cell) {
		return;
	}
	xf.removeClass(cell, 'over');
}

xf.GridSection.prototype.findField = function(target) {
	var cellEl = this.findCell(target);
	return this.fieldMap[cellEl.id];
}

xf.GridSection.prototype.moveTo = function(field, target) {
	var cellEl = this.findCell(target);
	var position = this.getPosition(cellEl);

	if (field.row == position.row && field.col == position.col) {
		return;
	}

	var fieldId = this.id + '-' + field.row + '-' + field.col;
	
	delete this.fieldMap[fieldId];
	xf.$(field.parentId).innerHTML = '';

	var row = position.row;
	var col = position.col;
	field.row = row;
	field.col = col;

	var fieldId = this.id + '-' + field.row + '-' + field.col;
	this.fieldMap[this.id + '-' + field.row + '-' + field.col] = field;

	field.parentId = cellEl.id;
	field.render();
}

xf.GridSection.prototype.setValue = function(data) {
	for (var key in this.fieldMap) {
		var field = this.fieldMap[key];
		var value = data[field.name];
		if (value) {
			field.setValue(value);
		}
	}
}

;

xf.Proxy = function() {
	this.id = 'xf-proxy';
	this.status = 'uninitialized';
	this.init();
}

xf.Proxy.prototype.init = function() {
	if (this.status == 'uninitialized') {
		var el = document.createElement('div');
		el.id = this.id;
		el.innerHTML = '&nbsp;';
		el.style.position = 'absolute';
		el.style.top = -100 + 'px';
		el.style.left = -100 + 'px';
		el.style.zIndex = 10000;
		el.style.width = '50px';
		el.style.backgroundColor = '#DDDDDD';
		el.style.border = 'dotted 1px gray';
		document.body.appendChild(el);

		this.status = 'initialized';
	}
}

xf.Proxy.prototype.move = function(x, y) {
	var el = xf.$(this.id);
	el.style.top = y + 'px';
	el.style.left = x + 'px';
}

xf.Proxy.prototype.hide = function() {
	this.move(-100, -100);
}

;

xf.field.attrText = function (){return '标题';}
xf.field.attrLabel = function (){return '标签名';}
xf.field.attrName = function (){return '控件名称';}
xf.field.attrAlias = function (){return '报表列名';}
xf.field.attrMinVal = function (){return '最小值';}
xf.field.attrMaxVal = function (){return '最大值';}
xf.field.attrPrecision = function (){return '小数位精度';}
xf.field.attrItems = function (){return '数据选项';}
xf.field.attrDic = function (){return '数据字典';}
xf.field.attrResources = function (){return '值源';}
xf.field.attrDataType = function (){return '数据类型';}
xf.field.attrRequired = function (){return '必输项';}
xf.field.attrReadOnly = function (){return '只读项';}
xf.field.attrReportFlag = function (){return '报表标志';}
xf.field.attrTxtAlign = function (){return '水平位置';}
xf.field.attrTxtColor = function (){return '字体颜色';}
;

xf.field.FieldFactory = function() {
	this.fieldTypeMap = {
		label: xf.field.Label,
		textfield: xf.field.TextField,
		numberbox: xf.field.Numberbox,
		calculator: xf.field.Calculator,
		upperdigit: xf.field.UpperDigit,
		password: xf.field.Password,
		textarea: xf.field.TextArea,
		select: xf.field.Select,
		radio: xf.field.Radio,
		checkbox: xf.field.Checkbox,
		fileupload: xf.field.FileUpload,
		datepicker: xf.field.DatePicker,
		userpicker: xf.field.UserPicker,
		userselection: xf.field.UserSelection,
		proselection: xf.field.ProSelection	//项目简称控件
	};
}

xf.field.FieldFactory.prototype.create = function(type, parentNode) {
	var constructor = this.fieldTypeMap[type];
	var field = new constructor(parentNode);
	return field;
}

;

xf.field.Label = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.align = 'center';
	this.color = 'black';
	this.name = 'label-' + this.row + '-' + this.col; 
	this.text = "text";
}

xf.field.Label.prototype.render = function() {
	this.updateText(this.text);
}

xf.field.Label.prototype.doExport = function() {
	return '{"type":"label","row":' + this.row
		+ ',"col":' + this.col
		+ ',"align":"' + this.align
		+ '","color":"' + this.color
		+ '","text":"' + this.text
		+ '"}';
}

xf.field.Label.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrLabel(), this.text, this.updateText, this, formNode);
	
	var alignData = new Array();
	alignData[0] = {'name':'居左','value':'left'};
	alignData[1] = {'name':'居中','value':'center'};
	alignData[2] = {'name':'居右','value':'right'};
	xf.createSelect(xf.field.attrTxtAlign(), this.align, this.updateTextAlign, this, formNode,alignData);
	
	var colorData = new Array();
	colorData[0] = {'name':'黑色','value':'black'};
	colorData[1] = {'name':'红色','value':'red'};
	colorData[2] = {'name':'橙色','value':'orange'};
	colorData[3] = {'name':'黄色','value':'yellow'};
	colorData[4] = {'name':'绿色','value':'green'};
	colorData[5] = {'name':'青色','value':'cyan'};
	colorData[6] = {'name':'蓝色','value':'blue'};
	colorData[7] = {'name':'紫色','value':'purple'};
	xf.createSelect(xf.field.attrTxtColor(), this.color, this.updateTextColor, this, formNode,colorData);
}

xf.field.Label.prototype.updateText = function(text) {
	this.text = text;
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	parentNode.innerHTML = 
		'<div class="xf-handler">'
		+ '<label style="display:block;text-align:'+(this.align?this.align:'center')+';color:'+(this.color?this.color:'black')+';margin-bottom:0px;">' + this.text + '</label>'
		+ '</div>';
}

xf.field.Label.prototype.updateTextAlign = function(align) {
	this.align = align;
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	parentNode.innerHTML = 
		'<div class="xf-handler">'
		+ '<label style="display:block;text-align:'+(this.align?this.align:'center')+';color:'+(this.color?this.color:'black')+';margin-bottom:0px;">' + this.text + '</label>'
		+ '</div>';
}

xf.field.Label.prototype.updateTextColor = function(color) {
	this.color = color;
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	parentNode.innerHTML = 
		'<div class="xf-handler">'
		+ '<label style="display:block;text-align:'+(this.align?this.align:'center')+';color:'+(this.color?this.color:'black')+';margin-bottom:0px;">' + this.text + '</label>'
		+ '</div>';
}

;

xf.field.TextField = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'textfield-' + this.row + '-' + this.col;
	this.required = false;
	this.readOnly = false;
	this.reportFlag = false;
	this.alias = this.name;
}

xf.field.TextField.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.TextField.prototype.doExport = function() {
	return '{"type":"textfield","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","alias":"' + this.alias
		+ '","required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ ',"reportFlag":' + this.reportFlag		//Add by William
		+ '}';
}

xf.field.TextField.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createField(xf.field.attrAlias(), this.alias, this.updateAlias, this, formNode);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
	//Add by William
	xf.createBooleanField(xf.field.attrReportFlag(), this.reportFlag, this.updateReportFlag, this, formNode);
}

xf.field.TextField.prototype.updateName = function(value) {
	this.name = value;
	this.display();
}

xf.field.TextField.prototype.display = function() {
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	var html ='<div class="xf-handler">';
	if(xf_f_flow_view){
		html += (this.value ? this.value : '');
	}else{
		html +='<input type="text" class="easyui-textbox" name="' + this.name + '" id="'+ this.name +'" alias="' + this.alias + '" ' 
				+ (this.readOnly ? 'readOnly' : '') + ' value="' + (this.value ? this.value : '') + '" '
				+ (this.required?' data-options="required:true" ':'') +'></input>';
	}
	html += '</div>';
	
	parentNode.innerHTML = html;
}

xf.field.TextField.prototype.updateAlias = function(value) {
	this.alias = value;
}

xf.field.TextField.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.TextField.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
	this.display();
}

//Add by William
xf.field.TextField.prototype.updateReportFlag = function(value) {
	this.reportFlag = value;
}

xf.field.TextField.prototype.setValue = function(value) {
	this.value = value;
	this.updateName(this.name);
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

;

xf.field.Numberbox = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'numberbox-' + this.row + '-' + this.col;
	this.required = false;
	this.readOnly = false;
	this.reportFlag = false;
	this.alias = this.name;
	this.minVal = 0;
	this.maxVal = 9999999999;
	this.precision=2;
}

xf.field.Numberbox.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.Numberbox.prototype.doExport = function() {
	return '{"type":"numberbox","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","alias":"' + this.alias
		+ '","minVal":' + this.minVal
		+ ',"maxVal":' + this.maxVal
		+ ',"precision":' + this.precision
		+ ',"required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ ',"reportFlag":' + this.reportFlag		//Add by William
		+ '}';
}

xf.field.Numberbox.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createField(xf.field.attrAlias(), this.alias, this.updateAlias, this, formNode);
	
	xf.createField(xf.field.attrMinVal(), this.minVal, this.updateMinVal, this, formNode);
	xf.createField(xf.field.attrMaxVal(), this.maxVal, this.updateMaxVal, this, formNode);
	xf.createField(xf.field.attrPrecision(), this.precision, this.updatePrecision, this, formNode);
	
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
	//Add by William
	xf.createBooleanField(xf.field.attrReportFlag(), this.reportFlag, this.updateReportFlag, this, formNode);
}

xf.field.Numberbox.prototype.updateName = function(value) {
	this.name = value;
	this.display();
}

xf.field.Numberbox.prototype.display = function() {
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	
	var html ='<div class="xf-handler">';
	if(xf_f_flow_view){
		html += (this.value ? this.value : '');
	}else{
		html +='<input type="text" class="easyui-numberbox" name="' + this.name + '" id="'+ this.name +'" alias="' + this.alias + '" ' 
				+ (this.readOnly ? 'readOnly' : '') + ' value="' + (this.value ? this.value : '') + '" '
				+ ' data-options="min:'+this.minVal+',max:'+this.maxVal+',precision:'+this.precision+',groupSeparator:\',\''+(this.required?',required:true"':'"')
				+'></input>';
	}
	html += '</div>';
	
	parentNode.innerHTML = html;
}

xf.field.Numberbox.prototype.updateAlias = function(value) {
	this.alias = value;
}

xf.field.Numberbox.prototype.updateMinVal = function(value) {
	this.minVal = value;
}

xf.field.Numberbox.prototype.updateMaxVal = function(value) {
	this.maxVal = value;
}

xf.field.Numberbox.prototype.updatePrecision = function(value) {
	this.precision = value;
}

xf.field.Numberbox.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.Numberbox.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
	this.display();
}

//Add by William
xf.field.Numberbox.prototype.updateReportFlag = function(value) {
	this.reportFlag = value;
}

xf.field.Numberbox.prototype.setValue = function(value) {
	this.value = value;
	this.updateName(this.name);
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

;

xf.field.Calculator = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'calculator-' + this.row + '-' + this.col;
	this.required = false;
	this.readOnly = false;
	this.reportFlag = false;
	this.alias = this.name;
	this.resources = '';
	this.dataType='text';
}

xf.field.Calculator.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.Calculator.prototype.doExport = function() {
	return '{"type":"calculator","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","alias":"' + this.alias
		+ '","resources":"' + this.resources
		+ '","dataType":"' + this.dataType
		+ '","required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ ',"reportFlag":' + this.reportFlag		//Add by William
		+ '}';
}

xf.field.Calculator.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	var selectionData = new Array();
	selectionData[0] = {'name':'文本','value':'text'};
	selectionData[1] = {'name':'数值','value':'number'};
	
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createField(xf.field.attrAlias(), this.alias, this.updateAlias, this, formNode);
	xf.createField(xf.field.attrResources(), this.resources, this.updateResources, this, formNode);
	xf.createSelect(xf.field.attrDataType(), this.dataType, this.updateDataType, this, formNode,selectionData);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
	//Add by William
	xf.createBooleanField(xf.field.attrReportFlag(), this.reportFlag, this.updateReportFlag, this, formNode);
}

xf.field.Calculator.prototype.updateName = function(value) {
	this.name = value;
	this.display();
}

xf.field.Calculator.prototype.display = function() {
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	
	var html ='<div class="xf-handler">';
	if(xf_f_flow_view){
		html += (this.value ? this.value : '');
	}else{
		html +='<input type="text" class="easyui-'+((this.dataType && this.dataType!='')?this.dataType:'text')+'box calculator" name="' + this.name + '" id="'+ this.name +'" alias="' + this.alias + '" resources="'+this.resources+'" ' + (this.readOnly ? 'readOnly' : '') + ' value="' + (this.value ? this.value : '') + '" style="margin-bottom:0px;cursor:move;">';
	}
	html += '</div>';
	
	parentNode.innerHTML = html;
}

xf.field.Calculator.prototype.updateAlias = function(value) {
	this.alias = value;
}

xf.field.Calculator.prototype.updateResources = function(value) {
	this.resources = value;
}

xf.field.Calculator.prototype.updateDataType = function(value) {
	this.dataType = value;
}

xf.field.Calculator.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.Calculator.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
	this.display();
}

//Add by William
xf.field.Calculator.prototype.updateReportFlag = function(value) {
	this.reportFlag = value;
}

xf.field.Calculator.prototype.setValue = function(value) {
	this.value = value;
	this.updateName(this.name);
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

;

xf.field.UpperDigit = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'upperdigit-' + this.row + '-' + this.col;
	this.required = false;
	this.readOnly = false;
	this.reportFlag = false;
	this.alias = this.name;
	this.resources = '';
}

xf.field.UpperDigit.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.UpperDigit.prototype.doExport = function() {
	return '{"type":"upperdigit","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","alias":"' + this.alias
		+ '","resources":"' + this.resources
		+ '","required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ ',"reportFlag":' + this.reportFlag		//Add by William
		+ '}';
}

xf.field.UpperDigit.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createField(xf.field.attrAlias(), this.alias, this.updateAlias, this, formNode);
	xf.createField(xf.field.attrResources(), this.resources, this.updateResources, this, formNode);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
	//Add by William
	xf.createBooleanField(xf.field.attrReportFlag(), this.reportFlag, this.updateReportFlag, this, formNode);
}

xf.field.UpperDigit.prototype.updateName = function(value) {
	this.name = value;
	this.display();
}

xf.field.UpperDigit.prototype.display = function() {
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	
	var html ='<div class="xf-handler">';
	if(xf_f_flow_view){
		html += (this.value ? this.value : '');
	}else{
		html +='<input type="text" class="easyui-textbox upperDigit" name="' + this.name + '" id="'+ this.name +'" alias="' + this.alias + '" resources="'+this.resources+'" ' + (this.readOnly ? 'readOnly' : '') + ' value="' + (this.value ? this.value : '零元整') + '" style="margin-bottom:0px;cursor:move;">';
	}
	html += '</div>';
	
	parentNode.innerHTML = html;
}

xf.field.UpperDigit.prototype.updateAlias = function(value) {
	this.alias = value;
}

xf.field.UpperDigit.prototype.updateResources = function(value) {
	this.resources = value;
}

xf.field.UpperDigit.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.UpperDigit.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
	this.display();
}

//Add by William
xf.field.UpperDigit.prototype.updateReportFlag = function(value) {
	this.reportFlag = value;
}

xf.field.UpperDigit.prototype.setValue = function(value) {
	this.value = value;
	this.updateName(this.name);
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

;

xf.field.Password = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'password-' + this.row + '-' + this.col;
	this.required = false;
	this.readOnly = false;
}

xf.field.Password.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.Password.prototype.doExport = function() {
	return '{"type":"password","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ '}';
}

xf.field.Password.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
}

xf.field.Password.prototype.updateName = function(value) {
	this.name = value;
	this.display();
}

xf.field.Password.prototype.display = function() {
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	parentNode.innerHTML = 
		'<div class="xf-handler">'
		+ '<input type="password" name="' + this.name + '" ' + (this.readOnly ? 'readOnly' : '') + ' style="margin-bottom:0px;cursor:move;">'
		+ '</div>';
}

xf.field.Password.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.Password.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
}

xf.field.Password.prototype.setValue = function(value) {
}

;

xf.field.TextArea = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'textarea-' + this.row + '-' + this.col;
	this.required = false;
	this.readOnly = false;
	this.reportFlag = false;		//Add by William
	this.alias = this.name;
}

xf.field.TextArea.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.TextArea.prototype.doExport = function() {
	return '{"type":"textarea","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","alias":"' + this.alias
		+ '","required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ ',"reportFlag":' + this.reportFlag	//Add by William
		+ '}';
}

xf.field.TextArea.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createField(xf.field.attrAlias(), this.alias, this.updateAlias, this, formNode);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
	//Add by William
	xf.createBooleanField(xf.field.attrReportFlag(), this.reportFlag, this.updateReportFlag, this, formNode);
}

xf.field.TextArea.prototype.updateName = function(value) {
	this.name = value;
	this.display();
}

xf.field.TextArea.prototype.display = function() {
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	var html = '<div class="xf-handler">';
	if(xf_f_flow_view){
		html += this.value ? this.value : '';
	}else{
		html += '<input class="easyui-textbox" name="' + this.name 
		+ '" id="'+ this.name +'" data-options="multiline:true" style="height:60px;width:258px;" '
		+(this.readOnly ? 'readOnly' : '')+' name="' + this.name+ '" '
		+' value="' + (this.value ? this.value : '') + '" '
		+ (this.required?' required ':'') +'></input>';
	}
	html += '</div>';
	parentNode.innerHTML = html;
}

xf.field.TextArea.prototype.updateAlias = function(value) {
	this.alias = value;
}

xf.field.TextArea.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.TextArea.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
	this.display();
}
//Add by William
xf.field.TextArea.prototype.updateReportFlag = function(value) {
	this.reportFlag = value;
}

xf.field.TextArea.prototype.setValue = function(value) {
	this.value = value;
	this.updateName(this.name);
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

;

xf.field.Select = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'select-' + this.row + '-' + this.col;
	this.items = '';
	this.required = false;
	this.readOnly = false;
	this.reportFlag = false;		//Add by William
	this.alias = this.name;
	this.dic='';
	this.dicsArr=[];
}

xf.field.Select.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.Select.prototype.doExport = function() {
	return '{"type":"select","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","alias":"' + this.alias
		+ '","items":"' + this.items
		+ '","dic":"' + this.dic
		+ '","required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ ',"reportFlag":' + this.reportFlag		//Add by William
		+ '}';
}

xf.field.Select.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createField(xf.field.attrAlias(), this.alias, this.updateAlias, this, formNode);
	xf.createSelect(xf.field.attrDic(), this.dic, this.updateDic, this, formNode,xf_f_select_dicData);
	xf.createField(xf.field.attrItems(), this.items, this.updateItems, this, formNode);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
	//Add by William
	xf.createBooleanField(xf.field.attrReportFlag(), this.reportFlag, this.updateReportFlag, this, formNode);
}

xf.field.Select.prototype.updateName = function(value) {
	this.name = value;
	this.updateItems(this.items);
}

xf.field.Select.prototype.updateDic = function(value) {
	//if(value){
	//	this.name = value;
	//	this.alias = value;
	//}
	this.dic = value;
	
	//FIEXE :ajax request dictionary datas
	
	//更新所有选项
	//if(arguments.length>1){
	//	var formNode = arguments[1];
	//	this.viewForm(formNode);
	//}
}

xf.field.Select.prototype.updateItems = function(value) {
	this.items = value;
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	var html = '<div class="xf-handler">';
	if(xf_f_flow_view){
		html+=(this.value ? this.value : '');
	}else{
		html+= '<select class="easyui-combobox" name="' + this.name + '" alias="' 
			+ this.alias + '" ' + (this.readOnly ? 'disabled' : '')
			+ (((!this.items || this.items.length==0) && (this.dic&&this.dic!=''))?
					('data-options="url:\'formTemplate!listDictionarys.do?dicKey='+this.dic+'\',method:\'get\',valueField:\'name\',textField:\'name\',panelHeight:\'auto\',editable:false"'):'')
			+ (this.required?' required ':'') + ' style="width:135px">';
		if(this.items){
			var array = this.items.split(',');
			for (var i = 0; i < array.length; i++) {
				var item = array[i];
				html += '<option value="' + item + '" ' + (this.value == item ? 'selected' : '') + '>' + item + '</option>';
			}
		}/**else if(this.dicsArr && this.dicsArr.length>0){
			var array = this.dicsArr;
			for (var i = 0; i < array.length; i++) {
				var item = array[i];
				html += '<option value="' + item.value + '" ' + (this.value == item.value ? 'selected' : '') + '>' + item.name + '</option>';
			}
		}**/
		html += '</select>'
	}
	html += '</div>';
	parentNode.innerHTML = html;
}

xf.field.Select.prototype.updateAlias = function(value) {
	this.alias = value;
}

xf.field.Select.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.Select.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
	this.updateItems(this.items);
}
//Add by William
xf.field.Select.prototype.updateReportFlag = function(value) {
	this.reportFlag = value;
}
xf.field.Select.prototype.setValue = function(value) {
	this.value = value;
	this.updateName(this.name);
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

;

xf.field.Radio = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'radio-' + this.row + '-' + this.col;
	this.items = '';
	this.required = false;
	this.readOnly = false;
	this.reportFlag = false;		//Add by William
	this.alias = this.name;
}

xf.field.Radio.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.Radio.prototype.doExport = function() {
	return '{"type":"radio","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","alias":"' + this.alias
		+ '","items":"' + this.items
		+ '","required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ ',"reportFlag":' + this.reportFlag		//Add by William
		+ '}';
}

xf.field.Radio.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createField(xf.field.attrAlias(), this.alias, this.updateAlias, this, formNode);
	xf.createField(xf.field.attrItems(), this.items, this.updateItems, this, formNode);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
	//Add by William
	xf.createBooleanField(xf.field.attrReportFlag(), this.reportFlag, this.updateReportFlag, this, formNode);
}

xf.field.Radio.prototype.updateName = function(value) {
	this.name = value;
	this.updateItems(this.items);
}

xf.field.Radio.prototype.updateItems = function(value) {
	this.items = value;
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	
	var html = '<div class="xf-handler'+(xf_f_flow_view?'':' textbox')+'" style="padding-right:3px;">';
	if(xf_f_flow_view){
		html+=(this.value ? this.value : '');
	}else{
		var array = this.items.split(',');
		for (var i = 0; i < array.length; i++) {
			var item = array[i];
			html += '<input type="radio" name="' + this.name + '" alias="' + this.alias + '" value="' + item + '" ' + (this.readOnly ? 'disabled' : '') + ' ' + (this.value == item ? 'checked' : '') +(this.required?' required ':'')+ '>';
			html += item;
		}
	}
	html += '</div>';
	parentNode.innerHTML = html;
}

xf.field.Radio.prototype.updateAlias = function(value) {
	this.alias = value;
}

xf.field.Radio.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.Radio.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
	this.updateItems(this.items);
}
//Add by William
xf.field.Radio.prototype.updateReportFlag = function(value) {
	this.reportFlag = value;
}
xf.field.Radio.prototype.setValue = function(value) {
	this.value = value;
	this.updateItems(this.items);
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

;

xf.field.Checkbox = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'checkbox-' + this.row + '-' + this.col;
	this.items = '';
	this.required = false;
	this.readOnly = false;
	this.reportFlag = false;		//Add by William
	this.alias = this.name;
}

xf.field.Checkbox.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.Checkbox.prototype.doExport = function() {
	return '{"type":"checkbox","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","alias":"' + this.alias
		+ '","items":"' + this.items
		+ '","required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ ',"reportFlag":' + this.reportFlag		//Add by William
		+ '}';
}

xf.field.Checkbox.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createField(xf.field.attrAlias(), this.alias, this.updateAlias, this, formNode);
	xf.createField(xf.field.attrItems(), this.items, this.updateItems, this, formNode);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
	//Add by William
	xf.createBooleanField(xf.field.attrReportFlag(), this.reportFlag, this.updateReportFlag, this, formNode);
}

xf.field.Checkbox.prototype.updateName = function(value) {
	this.name = value;
	this.updateItems(this.items);
}

xf.field.Checkbox.prototype.updateItems = function(value) {
	this.items = value;
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	var html = '<div class="xf-handler'+(xf_f_flow_view?'':' textbox')+'" style="padding-right:3px;">';
	
	if(xf_f_flow_view){
		html += (this.value ? this.value : '');
	}else{
		var array = this.items.split(',');
		for (var i = 0; i < array.length; i++) {
			var item = array[i];
			var checkedItem = false;
			if(this.value){
				//var vals = this.value.split(',');
				for(var j = 0; j<this.value.length; j++){
					if(this.value[j]==item){
						checkedItem = true;
						break;
					}
				}
			}
			html += '<input type="checkbox" name="' + this.name + '" alias="' + this.alias + '" value="' + item + '" ' + (this.readOnly ? 'disabled' : '') + ' ' + (checkedItem ? 'checked' : '') +(this.required?' required ':'')+ '>';
			html += item;
		}
	}
	html += '</div>';
	
	parentNode.innerHTML = html;
}

xf.field.Checkbox.prototype.updateAlias = function(value) {
	this.alias = value;
}

xf.field.Checkbox.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.Checkbox.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
	this.updateItems(this.items);
}
//Add by William
xf.field.Checkbox.prototype.updateReportFlag = function(value) {
	this.reportFlag = value;
}
xf.field.Checkbox.prototype.setValue = function(value) {
	this.value = value;
	this.updateName(this.name);
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

;

/*xf.field.FileUpload = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'fileupload-' + this.row + '-' + this.col;
	this.required = false;
	this.readOnly = false;
}

xf.field.FileUpload.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.FileUpload.prototype.doExport = function() {
	return '{"type":"fileupload","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ '}';
}

xf.field.FileUpload.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
}

xf.field.FileUpload.prototype.updateName = function(value) {
	this.name = value;
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	parentNode.innerHTML = 
		'<div class="xf-handler">'
		+ '<input type="file" name="' + this.name + '" ' + (this.readOnly ? 'readOnly' : '') + ' style="cursor:move;">'
		+ '</div>';
}

xf.field.FileUpload.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.FileUpload.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
}

xf.field.FileUpload.prototype.setValue = function(value) {
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

;
*/

xf.field.DatePicker = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'datepicker-' + this.row + '-' + this.col;
	this.required = false;
	this.readOnly = false;
	this.reportFlag = false;		//Add by William
	this.alias = this.name;
}

xf.field.DatePicker.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.DatePicker.prototype.doExport = function() {
	return '{"type":"datepicker","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","alias":"' + this.alias
		+ '","required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ ',"reportFlag":' + this.reportFlag		//Add by William
		+ '}';
}

xf.field.DatePicker.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createField(xf.field.attrAlias(), this.alias, this.updateAlias, this, formNode);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
	//Add by William
	xf.createBooleanField(xf.field.attrReportFlag(), this.reportFlag, this.updateReportFlag, this, formNode);
}

xf.field.DatePicker.prototype.updateName = function(value) {
	this.name = value;
	this.display();
}

xf.field.DatePicker.prototype.display = function() {
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	var html = '<div class="xf-handler">';
	if(xf_f_flow_view){
		html += (this.value ? this.value : '');
	}else{
		html += '<input class="easyui-datebox" name="' + this.name + '" '+ (this.readOnly ? 'readOnly="" ' : '') + ' value="' + (this.value ? this.value : '') +'"'
		+ (this.required?'required ':'')+'></input>';
	}
	html += '</div>';
	parentNode.innerHTML = html;
}

xf.field.DatePicker.prototype.updateAlias = function(value) {
	this.alias = value;
}

xf.field.DatePicker.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.DatePicker.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
	this.display();
}

//Add by William
xf.field.DatePicker.prototype.updateReportFlag = function(value) {
	this.reportFlag = value;
}

xf.field.DatePicker.prototype.setValue = function(value) {
	this.value = value;
	this.updateName(this.name);
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

;

xf.field.UserPicker = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'userpicker-' + this.row + '-' + this.col;
	this.required = false;
	this.readOnly = false;
	this.reportFlag = false;		//Add by William
	this.alias = this.name;
}

xf.field.UserPicker.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.UserPicker.prototype.doExport = function() {
	return '{"type":"userpicker","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","alias":"' + this.alias
		+ '","required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ ',"reportFlag":' + this.reportFlag		//Add by William
		+ '}';
}

xf.field.UserPicker.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createField(xf.field.attrAlias(), this.alias, this.updateAlias, this, formNode);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
	//Add by William
	xf.createBooleanField(xf.field.attrReportFlag(), this.reportFlag, this.updateReportFlag, this, formNode);
}

xf.field.UserPicker.prototype.updateName = function(value) {
	this.name = value;
	this.display();
}

xf.field.UserPicker.prototype.display = function() {
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	var html = '<div class="xf-handler">';
	if(xf_f_flow_view){
		html += (this.value ? this.value : '');
	}else{
		//html += '<input class="easyui-textbox userPickerBtn" data-options="icons:[{iconCls:\'icon-man\',handler:showUserPickTab}]" type="text" name="' + this.name + '" value="' + (this.value ? this.value : '') + '" '
		//+ (this.readOnly ? 'readOnly="" ' : '') + (this.required?'required ':'')+'></input>';
		html +='<select class="easyui-combobox" style="width:135px" name="' + this.name + '" '
		 + 'data-options="url:\'formTemplate!fetchUsers.do\',method:\'get\',valueField:\'name\',textField:\'name\',panelHeight:\'auto\',panelWidth:\'auto\',multiple:true"'
		 + (this.required?'required ':'')
		 + (this.readOnly ? 'disabled' : '') + '>';
		html += '</select>';
	}
	html += '</div>';
	parentNode.innerHTML = html;
}

xf.field.UserPicker.prototype.updateAlias = function(value) {
	this.alias = value;
}

xf.field.UserPicker.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.UserPicker.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
	this.display();
}
//Add by William
xf.field.UserPicker.prototype.updateReportFlag = function(value) {
	this.reportFlag = value;
}
xf.field.UserPicker.prototype.setValue = function(value) {
	this.value = value;
	this.updateName(this.name);
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

;

xf.field.UserSelection = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');
	
	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'userselection-' + this.row + '-' + this.col;
	this.alias = this.name;
	this.required = false;
	this.readOnly = false;
	this.reportFlag = false;		//Add by William
	this.collections=[];
	
	this.rolesArr = [];
	this.usersArr = [];
}

xf.field.UserSelection.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.UserSelection.prototype.doExport = function() {
	var text = '{"type":"userselection"'
		+',"row":' + this.row + ',"col":' + this.col 
		+ ',"required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ ',"reportFlag":' + this.reportFlag
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","alias":"'+this.alias+'","collections":[' ;
	
	text+='{"type":"select"'
		+ ',"name":"'+xf_f_name_prefix+this.name+'~'+ xf_f_name_prefix +'us_r~@0'+'"'
		+ '},{"type":"userpicker"'
		+ ',"name":"'+xf_f_name_prefix+this.name+'~'+ xf_f_name_prefix +'us_u~@1'+'"'
		+ '}';
	
	text+= ']}';
	
	return text;
}

xf.field.UserSelection.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createField(xf.field.attrAlias(), this.alias, this.updateAlias, this, formNode);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
	xf.createBooleanField(xf.field.attrReportFlag(), this.reportFlag, this.updateReportFlag, this, formNode);
}

xf.field.UserSelection.prototype.updateName = function(value) {
	this.name = value;
	this.display();
}

xf.field.UserSelection.prototype.display = function() {
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	//准备(用户)数据--开始
	 var usersjsonStr;
	 if(this.usersArr && this.usersArr.length>0){
		usersjsonStr='[';
		for (var i = 0; i < this.usersArr.length; i++) {
			var item = this.usersArr[i];
			usersjsonStr+='{"name":"'+item.name+'","value":"'+item.value+'"}';
			if (i != this.usersArr.length - 1) {
				usersjsonStr += ',';
			}
		}
		usersjsonStr+=']';
		usersjsonStr_tmp = usersjsonStr;
	 }
	//准备(用户)数据--结束
	
	//控件拖动初始化
	var html ='<div class="xf-handler UserSelection" style="margin:10px 0px">';
		if(!xf_f_flow_view){
			var innerHtml = '';
			innerHtml+= '<select class="easyui-combobox userSelectionCom" style="width:135px" name="'+this.name+'~'+xf_f_name_prefix+'us_r~@0'+'" id="'+this.name+'~'+xf_f_name_prefix+'us_r~@0'+'" '
			 + 'data-options="url:\'formTemplate!fetchRoles.do\',method:\'get\',valueField:\'name\',textField:\'name\',panelHeight:\'auto\'"'
			 + (this.required?'required ':'')
			 + (this.readOnly ? 'disabled' : '') + '>';
			innerHtml += '</select>';

			//innerHtml += '<input class="easyui-textbox userPicker" data-options="icons:[{iconCls:\'icon-man\',handler:showUserPickTab}]" type="text" name="'+this.name+'~'+xf_f_name_prefix+'us_u~@1'+'" alias="' + this.alias +'" '+ (this.readOnly ? 'readOnly="" ' : '') 
		 	//	+ (this.required?'required ':'')
		 	//	+' reportFlag='+this.reportFlag+' ' +'>';
			
			innerHtml +='<select class="easyui-combobox userSelectionCom" style="width:135px" name="'+this.name+'~'+xf_f_name_prefix+'us_u~@1'+'" id="'+this.name+'~'+xf_f_name_prefix+'us_u~@1'+'" '
			 + 'data-options="url:\'formTemplate!fetchUsers.do\',method:\'get\',valueField:\'name\',textField:\'name\',panelHeight:\'auto\',panelWidth:\'auto\',multiple:true"'
			 + (this.required?'required ':'')
			 + (this.readOnly ? 'disabled' : '') +' reportFlag='+this.reportFlag+' ' + '>';
			innerHtml += '</select>';
		 
			innerHtml += '<span class="add-on" style="padding-top: 2px; padding-bottom: 2px;border-radius: 0 4px 4px 0;"><i style="cursor:pointer;" class="icon-user '+(this.readOnly ? '':' userPickerBtn')+'"></i></span>';
			innerHtml += '<span '+(this.readOnly ? '':'class="userselectionAdd"')+' style="cursor: pointer;padding: 2px;font-size: 12px;">+</span>';
			innerHtml += '<span '+(this.readOnly ? '':'class="userselectionRemove"')+' style="cursor: pointer;padding: 2px;font-size: 12px;">-</span>';
		 html += innerHtml;
		 UserSelection_new_inner_html = innerHtml;
		}
		html += '</div>';
		 
	if(this.value){
		html = '';
		for(var key in this.value){
			var userSelValGroup = this.value[key];
			html+= this.loopItem(userSelValGroup);
		}
	}
	
	parentNode.innerHTML = html;
}

xf.field.UserSelection.prototype.loopItem = function(userSelValGroup) {
	
	var role0;
	var user1;
	if(userSelValGroup[0].innerName.indexOf('@0')>0){
		role0 = userSelValGroup[0].value;
	}else{
		role0 = userSelValGroup[1].value;
	}
	
	if(userSelValGroup[1].innerName.indexOf('@1')>0){
		user1 = userSelValGroup[1].value;
	}else{
		user1 = userSelValGroup[0].value;
	}
	
	var html ='<div class="xf-handler" style="margin:10px 0px">';
	if(xf_f_flow_view){
		html += role0+':'+user1;
	}else{
		 html += '<select class="easyui-combobox userSelectionCom" style="width:135px" name="'+this.name+'-'+xf_f_name_prefix+'us_r-@0'+'" ';
		 if(this.rolesArr && this.rolesArr.length>0){
			for (var i = 0; i < this.rolesArr.length; i++) {
				var item = this.rolesArr[i];
				html += '<option value="' + item.name + '" ' + (role0 == item.name ? 'selected' : '') + '>' + item.name + '</option>';
			}
		 }
		 html += '</select>';
		 html += '<input class="easyui-textbox" type="text" name="'+userSelValGroup[1].innerName+'" '+ (this.readOnly ? 'readOnly="" ' : '') + 'required='
		 		+this.required+' class="userPicker" value="' + (user1?user1:'') 
		 		+ '" style="margin-bottom:0px;cursor:move;">';
		 html += '<span class="add-on" style="padding-top: 2px; padding-bottom: 2px;border-radius: 0 4px 4px 0;"><i style="cursor:pointer;" class="icon-user '+(this.readOnly ? '':' userPickerBtn')+'"></i></span>';
	}
		 
	html += '</div>';
		 
	return html;
}

xf.field.UserSelection.prototype.updateAlias = function(value) {
	this.alias = value;
}

xf.field.UserSelection.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.UserSelection.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
	this.display();
}
//Add by William
xf.field.UserSelection.prototype.updateReportFlag = function(value) {
	this.reportFlag = value;
}
xf.field.UserSelection.prototype.setValue = function(value) {
	this.value = value;
	this.updateName(this.name);
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

;

xf.field.ProSelection = function(parentNode) {
	if (!parentNode) {
		return;
	}
	var parentId = parentNode.id;
	var array = parentId.split('-');

	this.parentId = parentId;
	this.row = array[3];
	this.col = array[4];
	this.name = 'proselection-' + this.row + '-' + this.col;
	this.items = '';
	this.required = false;
	this.readOnly = false;
	this.reportFlag = false;
	this.alias = this.name;
	this.dic='';
	this.dicsArr=[];
}

xf.field.ProSelection.prototype.render = function() {
	this.updateName(this.name);
}

xf.field.ProSelection.prototype.doExport = function() {
	return '{"type":"proselection","row":' + this.row
		+ ',"col":' + this.col
		+ ',"name":"' + xf_f_name_prefix + this.name
		+ '","alias":"' + this.alias
		+ '","items":"' + this.items
		+ '","dic":"' + this.dic
		+ '","required":' + this.required
		+ ',"readOnly":' + this.readOnly
		+ ',"reportFlag":' + this.reportFlag		//Add by William
		+ '}';
}

xf.field.ProSelection.prototype.viewForm = function(formNode) {
	formNode.innerHTML = '';
	xf.createField(xf.field.attrName(), this.name, this.updateName, this, formNode);
	xf.createField(xf.field.attrAlias(), this.alias, this.updateAlias, this, formNode);
	xf.createSelect(xf.field.attrDic(), this.dic, this.updateDic, this, formNode);
	xf.createField(xf.field.attrItems(), this.items, this.updateItems, this, formNode);
	xf.createBooleanField(xf.field.attrRequired(), this.required, this.updateRequired, this, formNode);
	xf.createBooleanField(xf.field.attrReadOnly(), this.readOnly, this.updateReadOnly, this, formNode);
	//Add by William
	xf.createBooleanField(xf.field.attrReportFlag(), this.reportFlag, this.updateReportFlag, this, formNode);
}

xf.field.ProSelection.prototype.updateName = function(value) {
	this.name = value;
	this.updateItems(this.items);
}

xf.field.ProSelection.prototype.updateDic = function(value) {
	if(value){
		this.name = value;
		this.alias = value;
	}
	this.dic = value;
	
	//更新所有选项
	if(arguments.length>1){
		var formNode = arguments[1];
		this.viewForm(formNode);
	}
}

xf.field.ProSelection.prototype.updateItems = function(value) {
	this.items = value;
	var parentNode = xf.$(this.parentId);
	if(!parentNode){return false;}
	var html = '<div class="xf-handler">';
	if(xf_f_flow_view){
		html += (this.value ? this.value : '');
	}else{
		html+= '<select class="easyui-combobox xf-proselection" style="width:135px" name="'+this.name+'" '
		+ 'data-options="url:\'formTemplate!listProjects.do\',method:\'get\',valueField:\'value\',textField:\'name\',panelHeight:\'auto\',panelWidth:\'auto\',editable:false"'
		+ (this.required?'required ':'')
		+ (this.readOnly ? 'disabled' : '') + '></select>';
	}
	html += '</div>';
	parentNode.innerHTML = html;
}

xf.field.ProSelection.prototype.updateAlias = function(value) {
	this.alias = value;
}

xf.field.ProSelection.prototype.updateRequired = function(value) {
	this.required = value;
}

xf.field.ProSelection.prototype.updateReadOnly = function(value) {
	this.readOnly = value;
	this.updateItems(this.items);
}
//Add by William
xf.field.ProSelection.prototype.updateReportFlag = function(value) {
	this.reportFlag = value;
}
xf.field.ProSelection.prototype.setValue = function(value) {
	this.value = value;
	this.updateName(this.name);
	if (this.readOnly) {
		var parentNode = xf.$(this.parentId);
		if(!parentNode){return false;}
		parentNode.innerHTML = value;
	}
}

