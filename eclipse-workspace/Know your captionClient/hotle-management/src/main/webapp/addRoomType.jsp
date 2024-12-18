<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter un Type de Chambre</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h2 {
            margin-bottom: 20px;
            color: #343a40;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
    </style>
    <script>
        function validateForm() {
            const label = document.getElementById('label').value;
            const capacity = document.getElementById('capacity').value;
            const price = document.getElementById('price').value;
            let isValid = true;
            let message = '';

            // Validate label
            if (label.trim() === '') {
                message += 'Le libellé est requis.\n';
                isValid = false;
            }

            // Validate capacity
            if (capacity <= 0 || !Number.isInteger(Number(capacity))) {
                message += 'La capacité maximale doit être un nombre entier positif.\n';
                isValid = false;
            }

            // Validate price
            if (price < 0) {
                message += 'Le prix doit être un nombre positif.\n';
                isValid = false;
            }

            if (!isValid) {
                alert(message);
            }

            return isValid;
        }
    </script>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">Ajouter un Type de Chambre</h2>
        <form action="AddRoomTypeServlet" method="post" onsubmit="return validateForm();">
            <div class="form-group">
                <label for="label">Libellé</label>
                <input type="text" class="form-control" id="label" name="label" placeholder="Entrez le libellé" required>
            </div>
            <div class="form-group">
                <label for="capacity">Capacité Maximale</label>
                <input type="number" class="form-control" id="capacity" name="capacity" placeholder="Entrez la capacité maximale" required min="1">
            </div>
            <div class="form-group">
                <label for="price">Prix (€)</label>
                <input type="number" class="form-control" id="price" name="price" placeholder="Entrez le prix" required min="0" step="0.01">
            </div>
            <button type="submit" class="btn btn-primary btn-block">Ajouter</button>
        </form>
        <hr>
        <a href="roomTypesList.jsp" class="btn btn-secondary btn-block">Retour à la liste des types de chambre</a>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>