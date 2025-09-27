$(function() {
    // Máscaras
    $('#cpf').mask('000.000.000-00');
    $('#cep').mask('00000-000');

    // Validação customizada Bootstrap
    const form = document.getElementById('contractForm');
    form.addEventListener('submit', function (event) {
        let valid = true;

        // Datas
        const start = new Date(form.startDate.value);
        const end = new Date(form.endDate.value);
        if (end && start && end < start) {
            valid = false;
            form.endDate.classList.add('is-invalid');
        } else {
            form.endDate.classList.remove('is-invalid');
        }

        // CPF
        const cpf = form.cpf.value.replace(/\D/g, '');
        if (!isValidCPF(cpf)) {
            valid = false;
            form.cpf.classList.add('is-invalid');
        } else {
            form.cpf.classList.remove('is-invalid');
        }

        // CEP
        const cep = form.cep.value.replace(/\D/g, '');
        if (cep.length !== 8) {
            valid = false;
            form.cep.classList.add('is-invalid');
        } else {
            form.cep.classList.remove('is-invalid');
        }

        if (!valid) {
            event.preventDefault();
            event.stopPropagation();
        }
    }, false);
});

// CPF Validator (mesma lógica da sua classe Java)
function isValidCPF(cpf) {
    if (!cpf || cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false;
    let soma = 0, resto;
    for (let i = 1; i <= 9; i++) soma += parseInt(cpf.substring(i-1, i)) * (11 - i);
    resto = (soma * 10) % 11;
    if (resto === 10 || resto === 11) resto = 0;
    if (resto !== parseInt(cpf.substring(9, 10))) return false;
    soma = 0;
    for (let i = 1; i <= 10; i++) soma += parseInt(cpf.substring(i-1, i)) * (12 - i);
    resto = (soma * 10) % 11;
    if (resto === 10 || resto === 11) resto = 0;
    return resto === parseInt(cpf.substring(10, 11));
}