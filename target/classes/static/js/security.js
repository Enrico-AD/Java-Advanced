    document.body.addEventListener('htmx:configRequest', (event) => {
        const token = document.querySelector("meta[name='_csrf']").content;
        const header = document.querySelector("meta[name='_csrf_header']").content;
        event.detail.headers[header] = token;
    });