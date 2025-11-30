//會員購物紀錄//
let shoppingRecordOrders;
let shoppingRecordOrderItemsList;

function valueOrNull (value) {
	if(value === undefined || value === null || value === ''){
		return null;
	}else{
		return value;
	}
};

const tbody = document.querySelector('tbody');
const dt_order_table = document.querySelector('.datatables-order');

let borderColor, bodyBg, headingColor;
borderColor = config.colors.borderColor;
bodyBg = config.colors.bodyBg;
headingColor = config.colors.headingColor;

const paymentMethodObj = {
  Cash:'現金',
  Card:'信用卡',
  CREDIT_CARD:'信用卡'
};

const statusObj = {
  PAID:      { class: 'text-success',  text: '已付款' },
  WAIT_PAID: { class: 'text-warning',  text: '待付款' },
  CANCELLED: { class: 'text-secondary',text: '已取消' }
};
// 取值
// paymentObj[status].class
// paymentObj[status].text

shoppingRecord();
function shoppingRecord(){
	fetch('shoppingRecord')
	.then(resp => resp.json())
	.then(body => {
		shoppingRecordOrders = body.Orders;
		shoppingRecordOrderItemsList = body.Orderitems;
		if (dt_order_table) {
			const dt_products = new DataTable(dt_order_table, {
				data: shoppingRecordOrders,   // ← 直接把 JSON 塞給 DataTables
				columns: [
					{ data: null,
						// For Responsive
						className: 'control',
						searchable: false,
						orderable: false,
						responsivePriority: 2,
						targets: 0,
						render: function (data, type, full, meta) {
							return '';
						}
					}, // checkbox 或空欄
					{ data: null,
						// For Checkboxes
						targets: 1,
						orderable: false,
						searchable: false,
						responsivePriority: 3,
						checkboxes: true,
						render: function () {
							return '<input type="checkbox" class="dt-checkboxes form-check-input">';
						},
						checkboxes: {
							selectAllRender: '<input type="checkbox" class="form-check-input">'
						}
					}, // checkbox 或空欄
					{ data: 'orderId' },
					{ data: 'createdAt' },
					{ data: null,
						render: function(row) {
							const orderId = row.orderId;
							const items = row.orderitems;
							if (!items || items.length === 0) {
								return ''
							};
							// 過濾出 orderId 相同的 items（保險寫法）
							const matchedItems = items.filter(item => item.orderId === orderId);
							return matchedItems.map(item => item.title).join('<br>');
						}
					},
					{ data: 'payAmount' },
					{ data: 'paymentMethod', 
						render: function(items) {
							return paymentMethodObj[items];
						}
					},
					{ data: 'status',
						render: function(items) {
							return statusObj[items].text;
						}
					}
				],
				select: {
					style: 'multi',
					selector: 'td:nth-child(2)'
				},
				order: [3, 'asc'],
				layout: {
					topStart: {
						search: {
							placeholder: 'Search Order',
							text: '_INPUT_'
						}
					},
					topEnd: {
						rowClass: 'row mx-3 my-0 justify-content-between',
						features: [
							{
								pageLength: {
									menu: [10, 25, 50, 100],
									text: '_MENU_'
								}
							},
							{
								buttons: [
									{
										extend: 'collection',
										className: 'btn btn-label-primary dropdown-toggle',
										text: '<span class="d-flex align-items-center gap-1"><i class="icon-base ti tabler-upload icon-xs"></i> <span class="d-none d-sm-inline-block">Export</span></span>',
										buttons: [
											{
												extend: 'print',
												text: `<span class="d-flex align-items-center"><i class="icon-base ti tabler-printer me-1"></i>Print</span>`,
												className: 'dropdown-item',
												exportOptions: {
													columns: [3, 4, 5, 6, 7],
													format: {
														body: function (inner, coldex, rowdex) {
															if (inner.length <= 0) return inner;
															const el = new DOMParser().parseFromString(inner, 'text/html').body.childNodes;
															let result = '';
															el.forEach(item => {
																if (item.classList && item.classList.contains('user-name')) {
																	result += item.lastChild.firstChild.textContent;
																} else {
																	result += item.textContent || item.innerText || '';
																}
															});
															return result;
														}
													}
												},
												customize: function (win) {
													win.document.body.style.color = headingColor;
													win.document.body.style.borderColor = borderColor;
													win.document.body.style.backgroundColor = bodyBg;
													const table = win.document.body.querySelector('table');
													table.classList.add('compact');
													table.style.color = 'inherit';
													table.style.borderColor = 'inherit';
													table.style.backgroundColor = 'inherit';
												}
											},
											{
												extend: 'csv',
												text: `<span class="d-flex align-items-center"><i class="icon-base ti tabler-file me-1"></i>Csv</span>`,
												className: 'dropdown-item',
												exportOptions: {
													columns: [3, 4, 5, 6, 7],
													format: {
														body: function (inner, coldex, rowdex) {
															if (inner.length <= 0) return inner;
															const el = new DOMParser().parseFromString(inner, 'text/html').body.childNodes;
															let result = '';
															el.forEach(item => {
																if (item.classList && item.classList.contains('user-name')) {
																	result += item.lastChild.firstChild.textContent;
																} else {
																	result += item.textContent || item.innerText || '';
																}
															});
															return result;
														}
													}
												}
											},
											{
												extend: 'excel',
												text: `<span class="d-flex align-items-center"><i class="icon-base ti tabler-upload me-1"></i>Excel</span>`,
												className: 'dropdown-item',
												exportOptions: {
													columns: [3, 4, 5, 6, 7],
													format: {
														body: function (inner, coldex, rowdex) {
															if (inner.length <= 0) return inner;
															const el = new DOMParser().parseFromString(inner, 'text/html').body.childNodes;
															let result = '';
															el.forEach(item => {
																if (item.classList && item.classList.contains('user-name')) {
																	result += item.lastChild.firstChild.textContent;
																} else {
																	result += item.textContent || item.innerText || '';
																}
															});
															return result;
														}
													}
												}
											},
											{
												extend: 'pdf',
												text: `<span class="d-flex align-items-center"><i class="icon-base ti tabler-file-text me-1"></i>Pdf</span>`,
												className: 'dropdown-item',
												exportOptions: {
													columns: [3, 4, 5, 6, 7],
													format: {
														body: function (inner, coldex, rowdex) {
															if (inner.length <= 0) return inner;
															const el = new DOMParser().parseFromString(inner, 'text/html').body.childNodes;
															let result = '';
															el.forEach(item => {
																if (item.classList && item.classList.contains('user-name')) {
																	result += item.lastChild.firstChild.textContent;
																} else {
																	result += item.textContent || item.innerText || '';
																}
															});
															return result;
														}
													}
												}
											},
											{
												extend: 'copy',
												text: `<i class="icon-base ti tabler-copy me-1"></i>Copy`,
												className: 'dropdown-item',
												exportOptions: {
													columns: [3, 4, 5, 6, 7],
													format: {
														body: function (inner, coldex, rowdex) {
															if (inner.length <= 0) return inner;
															const el = new DOMParser().parseFromString(inner, 'text/html').body.childNodes;
															let result = '';
															el.forEach(item => {
																if (item.classList && item.classList.contains('user-name')) {
																	result += item.lastChild.firstChild.textContent;
																} else {
																	result += item.textContent || item.innerText || '';
																}
															});
															return result;
														}
													}
												}
											}
										]
									}
								]
							}
						]
					},
					bottomStart: {
						rowClass: 'row mx-3 justify-content-between',
						features: ['info']
					},
					bottomEnd: 'paging'
				},
				language: {
					paginate: {
						next: '<i class="icon-base ti tabler-chevron-right scaleX-n1-rtl icon-18px"></i>',
						previous: '<i class="icon-base ti tabler-chevron-left scaleX-n1-rtl icon-18px"></i>',
						first: '<i class="icon-base ti tabler-chevrons-left scaleX-n1-rtl icon-18px"></i>',
						last: '<i class="icon-base ti tabler-chevrons-right scaleX-n1-rtl icon-18px"></i>'
					}
				},
				// For responsive popup
				responsive: {
					details: {
						display: DataTable.Responsive.display.modal({
							header: function (row) {
								const data = row.data();
								return 'Details of ' + data['customer'];
							}
						}),
						type: 'column',
						renderer: function (api, rowIdx, columns) {
							const data = columns
								.map(function (col) {
									return col.title !== '' // Do not show row in modal popup if title is blank (for check box)
										? `<tr data-dt-row="${col.rowIndex}" data-dt-column="${col.columnIndex}">
												<td>${col.title}:</td>
												<td>${col.data}</td>
											</tr>`
										: '';
								})
								.join('');

							if (data) {
								const div = document.createElement('div');
								div.classList.add('table-responsive');
								const table = document.createElement('table');
								div.appendChild(table);
								table.classList.add('table');
								const tbody = document.createElement('tbody');
								tbody.innerHTML = data;
								table.appendChild(tbody);
								return div;
							}
							return false;
						}
					}
				}
			});

			//? The 'delete-record' class is necessary for the functionality of the following code.
			document.addEventListener('click', function (e) {
				if (e.target.classList.contains('delete-record')) {
					dt_products.row(e.target.closest('tr')).remove().draw();
					const modalEl = document.querySelector('.dtr-bs-modal');
					if (modalEl && modalEl.classList.contains('show')) {
						const modal = bootstrap.Modal.getInstance(modalEl);
						modal?.hide();
					}
				}
			});
		}

		// Filter form control to default size
		// ? setTimeout used for order-list table initialization
		setTimeout(() => {
			const elementsToModify = [
				{ selector: '.dt-buttons .btn', classToRemove: 'btn-secondary', classToAdd: 'btn-label-secondary' },
				{ selector: '.dt-search .form-control', classToRemove: 'form-control-sm', classToAdd: 'ms-0' },
				{ selector: '.dt-length .form-select', classToRemove: 'form-select-sm' },
				{ selector: '.dt-length', classToAdd: 'mt-md-6 mt-0' },
				{ selector: '.dt-layout-table', classToRemove: 'row mt-2' },
				{ selector: '.dt-layout-end', classToAdd: 'px-3 mt-0' },
				{
					selector: '.dt-layout-end .dt-buttons',
					classToAdd: 'gap-2 px-3 mt-0 mb-md-0 mb-6'
				},
				{
					selector: '.dt-layout-end .dt-buttons .btn-group',
					classToAdd: 'mx-auto'
				},
				{ selector: '.dt-layout-start', classToAdd: 'px-3 mt-0' },
				{ selector: '.dt-layout-full', classToRemove: 'col-md col-12', classToAdd: 'table-responsive' }
			];

			// Delete record
			elementsToModify.forEach(({ selector, classToRemove, classToAdd }) => {
				document.querySelectorAll(selector).forEach(element => {
					if (classToRemove) {
						classToRemove.split(' ').forEach(className => element.classList.remove(className));
					}
					if (classToAdd) {
						classToAdd.split(' ').forEach(className => element.classList.add(className));
					}
				});
			});
		}, 100);
	});
}
