document.addEventListener("DOMContentLoaded", function() {
    document.addEventListener("htmx:afterRequest", function(evt) {
        if (evt.detail.xhr.status === 200 && evt.target.closest("form")) {
            const modalEl = document.getElementById("mainModal");
            if (modalEl) {
                const modal = bootstrap.Modal.getInstance(modalEl);
                modal.hide();
            }
        }
    });
});

document.body.addEventListener("htmx:afterSwap", (e) => {
    if (e.detail.target.id === "modalContent") {
        let modal = bootstrap.Modal.getOrCreateInstance(document.getElementById("mainModal"));
        modal.show();
    }
});