<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Bank Statement - ${customer.name}</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h2 { color: #2C3E50; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .credit { color: green; }
        .debit { color: red; }
    </style>
</head>
<body>
    <h2>Bank Statement</h2>
    <p><strong>Name:</strong> ${customer.name}</p>
    <p><strong>Email:</strong> ${customer.email}</p>
    <p><strong>Account No:</strong> ${customer.accountNumber}</p>
    <p><strong>Address:</strong> ${customer.address}</p>

    <table>
        <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Type</th>
                <th>Amount</th>
                <th>Balance</th>
            </tr>
        </thead>
        <tbody>
            <#list transactions as txn>
            <tr>
                <td>${txn.date}</td>
                <td>${txn.description}</td>
                <td class="${txn.type?lower_case}">${txn.type}</td>
                <td>${txn.amount}</td>
                <td>${txn.balance}</td>
            </tr>
            </#list>
        </tbody>
    </table>
</body>
</html>
