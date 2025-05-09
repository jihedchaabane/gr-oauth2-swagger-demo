window.onload = function() {
    // Attendre que Swagger UI soit complètement chargé
    const ui = SwaggerUIBundle({
        onComplete: () => {
            // Fonction pour vérifier si un token est présent
            function checkAuthorization() {
                const auth = ui.auth.get();
                const isAuthorized = auth && auth.authenticated && auth.authenticated["oauth2"];
                const authorizeButton = document.querySelector(".auth-wrapper .authorize");

                if (isAuthorized && authorizeButton) {
                    authorizeButton.style.display = "none"; // Masquer le bouton Authorize
                } else if (authorizeButton) {
                    authorizeButton.style.display = "block"; // Afficher le bouton si non authentifié
                }
            }

            // Vérifier l'état de l'authentification initialement
            checkAuthorization();

            // Écouter les changements d'état d'authentification
            ui.auth.on("auth_change", checkAuthorization);
        }
    });
};