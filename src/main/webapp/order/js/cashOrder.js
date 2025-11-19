//現金付款處理清單
let cashOrders;
let cashOrderItemsList;

function valueOrNull (value) {
	if(value === undefined || value === null || value === ''){
		return null;
	}else{
		return value;
	}
};

const tbody = document.querySelector('tbody');
const dt_invoice_table = document.querySelector('.invoice-list-table');
const statusObj = {
  PAID:      { class: 'text-success',  text: '已付款' },
  WAIT_PAID: { class: 'text-warning',  text: '待付款' },
};
// 取值
// paymentObj[status].class
// paymentObj[status].text

cashOrder();
function cashOrder(){
	fetch('cashOrder')
	.then(resp => resp.json())
	.then(body => {
		cashOrders = body.Orders;
		cashOrderItemsList = body.Orderitems;
    if (dt_invoice_table) {
      const dt_invoice = new DataTable(dt_invoice_table, {
        data: cashOrders,
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
					{ data: null,
						render: function(row) {
              const name = row.name;
              const email = row.email;
							if (!name || name.length === 0) {
								return ''
							};
              if (!email || email.length === 0) {
								return ''
							};
              return `
                <div class="d-flex justify-content-start align-items-center">
                  <div class="d-flex flex-column">
                    <a href="pages-profile-user.html" class="text-heading text-truncate"><span class="fw-medium">${name}</span></a>
                    <small class="text-truncate">${email}</small>
                  </div>
                </div>
              `;
            }
          },
					{ data: 'orderId' },
					{ data: 'createdAt'},
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
					{ data: 'payAmount'},
					{ data: 'status',
						render: function(items) {
							return statusObj[items].text;
						}
					},
          { data: null,
            render: function(row) {
              return `
                <button class="btn btn-primary pay-btn" data-order-id="${row.orderId}">
                  <span class="d-md-inline-block d-none">臨櫃付款</span>
                </button>
              `;
            }
					}
				],
        select: {
          style: 'multi',
          selector: 'td:nth-child(2)'
        },
        order: [[2, 'desc']],
        displayLength: 10,
        layout: {
          topStart: {
            rowClass: 'row m-3 my-0 justify-content-between',
            features: [
              {
                pageLength: {
                  menu: [10, 25, 50, 100],
                  text: 'Show_MENU_'
                }
              }
            ]
          },
          topEnd: {
            rowClass: 'row m-3 my-0 justify-content-between',
            features: [
              {
                search: {
                  placeholder: 'Search Order',
                  text: '_INPUT_'
                }
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
        responsive: {
          details: {
            display: DataTable.Responsive.display.modal({
              header: function (row) {
                const data = row.data();
                return 'Details of ' + data['client_name'];
              }
            }),
            type: 'column',
            renderer: function (api, rowIdx, columns) {
              const data = columns
                .map(function (col) {
                  return col.title !== '' // ? Do not show row in modal popup if title is blank (for check box)
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
        },
      });

      $('.invoice-list-table').on('click', '.pay-btn', function () {
        const btn = $(this);
        const orderId = btn.data('order-id');
        fetch('statusChange', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            orderId: valueOrNull(orderId)
          }),
        })
        .then(resp => resp.json())
        .then(body => {

          if (body.successful) {

            Swal.fire({
              title: '完成',
              text: body.message,
              icon: 'success',
              target: document.body
            });

            // 🔥 將該按鈕 disabled
            btn.addClass('disabled').prop('disabled', true);
            location.reload();
          } else {
            Swal.fire({
              title: '錯誤',
              text: body.message,
              icon: 'error',
              target: document.body
            });
          }
        });
      })

      function deleteRecord(event) {
        let row = document.querySelector('.dtr-expanded');
        if (event) {
          row = event.target.parentElement.closest('tr');
        }
        if (row) {
          dt_invoice.row(row).remove().draw();
        }
      }

      function bindDeleteEvent() {
        const invoiceTable = document.querySelector('.invoice-list-table');
        const modal = document.querySelector('.dtr-bs-modal');

        if (invoiceTable && invoiceTable.classList.contains('collapsed')) {
          if (modal) {
            modal.addEventListener('click', function (event) {
              if (event.target.parentElement.classList.contains('delete-record')) {
                const tooltipInstance = bootstrap.Tooltip.getInstance(event.target.parentElement);
                if (tooltipInstance) {
                  tooltipInstance.dispose();
                }
                deleteRecord();
                const closeButton = modal.querySelector('.btn-close');
                if (closeButton) closeButton.click(); // Simulates a click on the close button
              }
            });
          }
        } else {
          const tableBody = invoiceTable?.querySelector('tbody');
          if (tableBody) {
            tableBody.addEventListener('click', function (event) {
              if (event.target.parentElement.classList.contains('delete-record')) {
                const tooltipInstance = bootstrap.Tooltip.getInstance(event.target.parentElement);
                if (tooltipInstance) {
                  tooltipInstance.dispose();
                }
                deleteRecord(event);
              }
            });
          }
        }
      }

      // Initial event binding
      bindDeleteEvent();

      // Re-bind events when modal is shown or hidden
      document.addEventListener('show.bs.modal', function (event) {
        if (event.target.classList.contains('dtr-bs-modal')) {
          bindDeleteEvent();
        }
      });

      document.addEventListener('hide.bs.modal', function (event) {
        if (event.target.classList.contains('dtr-bs-modal')) {
          bindDeleteEvent();
        }
      });

      // Initialize tooltips on each table draw
      dt_invoice.on('draw', function () {
        const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
        tooltipTriggerList.forEach(tooltipTriggerEl => {
          new bootstrap.Tooltip(tooltipTriggerEl, {
            boundary: document.body
          });
        });
      });
    }



    // Filter form control to default size
    // ? setTimeout used for multilingual table initialization
    setTimeout(() => {
      const elementsToModify = [
        { selector: '.dt-buttons .btn', classToRemove: 'btn-secondary' },
        { selector: '.dt-buttons', classToRemove: 'btn-secondary' },
        { selector: '.dt-buttons.btn-group', classToAdd: 'mb-0' },
        { selector: '.dt-search .form-control', classToRemove: 'form-control-sm' },
        { selector: '.dt-length .form-select', classToRemove: 'form-select-sm' },
        { selector: '.dt-length', classToAdd: 'me-0 mb-md-6 mb-6' },
        {
          selector: '.dt-layout-end',
          classToRemove: 'justify-content-between ms-auto',
          classToAdd: 'justify-content-md-between justify-content-center d-flex flex-wrap gap-4 mb-sm-0 mb-4 mt-0'
        },
        {
          selector: '.dt-layout-start',
          classToRemove: 'd-md-flex me-auto justify-content-between',
          classToAdd: 'col-12 col-md-6 d-flex justify-content-center justify-content-md-start gap-2'
        },
        { selector: '.dt-layout-table', classToRemove: 'row mt-2' },
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
