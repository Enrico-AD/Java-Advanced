function submitContractForm(event, form) {
    event.preventDefault();

    fetch(form.action, {
        method: form.method,
        body: new FormData(form)
    })
    .then(resp => {
        if (resp.ok) {
            closeDialog();
            reloadContractsList();
        } else {
            return resp.text().then(html => {
                form.outerHTML = html;
            });
        }
    })
    .catch(err => console.error("Erro no envio do contrato:", err));
}

function closeDialog() {
   document.getElementById('dialog').innerHTML=''
}

function reloadContractsList() {
    const status = document.getElementById('statusFilter').value;
    const sortBy = document.getElementById('sortBy').value;
    const search = document.getElementById('searchInput').value;

    let url = '/list/contracts?';

    if (status) url += `status=${status}&`;
    if (sortBy) url += `sortBy=${sortBy}&`;
    if (search) url += `search=${encodeURIComponent(search)}&`;

    fetch(url)
        .then(resp => resp.text())
        .then(html => {
            document.getElementById('contracts-list').innerHTML = html;
        });
}

let currentSort = { field: 'startDate', direction: 'asc' };

function setSort(field) {
    if (currentSort.field === field) {
        // Se clicar no mesmo campo, inverte a ordem
        currentSort.direction = currentSort.direction === 'asc' ? 'desc' : 'asc';
    } else {
        // Novo campo: começa ascendente
        currentSort = { field, direction: 'asc' };
    }
    reloadContractsList();
}

function reloadContractsList() {
    const status = document.getElementById('statusFilter')?.value || '';
    const search = document.getElementById('searchInput')?.value || '';

    let url = `/list/contracts?sortBy=${currentSort.field}&direction=${currentSort.direction}`;

    if (status) url += `&status=${status}`;
    if (search) url += `&search=${encodeURIComponent(search)}`;

    fetch(url)
        .then(resp => resp.text())
        .then(html => {
            document.getElementById('contracts-list').innerHTML = html;
        });
}