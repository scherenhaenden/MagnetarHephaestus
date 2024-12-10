package UI.Main.Panels

import Common.FileTypeUtils // Make sure you have this import for FileTypeUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LeftPanelContent(
    initialInputText: String = defaultJson,
    onInputChange: (String) -> Unit,
    onCreateClasses: (String) -> Unit
) {
    if (initialInputText == "defaultJson") {
        onInputChange(defaultJson)
    }

    var inputText by remember { mutableStateOf(initialInputText) }
    var fileType by remember { mutableStateOf(FileTypeUtils.determineFileType(initialInputText)) }

    if (initialInputText == "defaultJson") {
        inputText = defaultJson
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            IconButton(onClick = { onInputChange("") }) {
                Icon(Icons.Filled.Delete, contentDescription = "Clean")
            }
            IconButton(onClick = { onCreateClasses(inputText) }) {
                Icon(Icons.Filled.Code, contentDescription = "Create Classes")
            }
        }

        TextField(
            value = inputText,
            onValueChange = {
                onInputChange(it)
                fileType = FileTypeUtils.determineFileType(it)
            },
            label = { Text("Enter text") },
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        )
    }
}





// Define your default JSON string
private val defaultJson = """
{
  "author": "Edward Flores",
  "modelName": "CustomerManagementModel",
  "createdDate": "2024-12-07T10:00:00Z",
  "outputLanguage": ["C#", "TypeScript", "Kotlin", "Python", "Java"],
  "modelDetails": {
    "domain": "CustomerManagement",
    "layer": "BusinessLogic",
    "direction": "Input"
  },
  "models": [
    {
      "name": "Customer",
      "description": "A representation of a customer in the system.",
      "example": {
        "id": "123e4567-e89b-12d3-a456-426614174000",
        "firstName": "John",
        "lastName": "Doe",
        "email": "john.doe@example.com",
        "isActive": true,
        "createdDate": "2024-12-07T10:00:00Z"
      },
      "fields": [
        {
          "name": "id",
          "dataType": "string",
          "isPrimitive": true,
          "description": "A unique identifier for the customer.",
          "example": "123e4567-e89b-12d3-a456-426614174000",
          "constraints": {
            "required": true,
            "format": "uuid"
          }
        },
        {
          "name": "firstName",
          "dataType": "string",
          "isPrimitive": true,
          "description": "The customer's first name.",
          "example": "John",
          "constraints": {
            "required": true,
            "minLength": 1,
            "maxLength": 50
          }
        },
        {
          "name": "lastName",
          "dataType": "string",
          "isPrimitive": true,
          "description": "The customer's last name.",
          "example": "Doe",
          "constraints": {
            "required": true,
            "minLength": 1,
            "maxLength": 50
          }
        },
        {
          "name": "email",
          "dataType": "string",
          "isPrimitive": true,
          "description": "The customer's email address.",
          "example": "john.doe@example.com",
          "constraints": {
            "required": true,
            "format": "email",
            "maxLength": 100
          }
        },
        {
          "name": "isActive",
          "dataType": "boolean",
          "isPrimitive": true,
          "description": "Whether the customer is currently active.",
          "example": true,
          "constraints": {
            "required": true
          }
        },
        {
          "name": "createdDate",
          "dataType": "string",
          "isPrimitive": true,
          "description": "The date and time when the customer was created.",
          "example": "2024-12-07T10:00:00Z",
          "constraints": {
            "required": true,
            "format": "date-time"
          }
        }
      ]
    },
    {
      "name": "Order",
      "description": "A representation of an order placed by a customer.",
      "example": {
        "orderId": "987e6543-e21d-12d3-a456-426614174001",
        "customerId": "123e4567-e89b-12d3-a456-426614174000",
        "orderDate": "2024-12-06T09:00:00Z",
        "status": "Pending",
        "items": [
          {
            "productId": "abc123",
            "quantity": 2,
            "price": 19.99
          }
        ]
      },
      "fields": [
        {
          "name": "orderId",
          "dataType": "string",
          "isPrimitive": true,
          "description": "A unique identifier for the order.",
          "example": "987e6543-e21d-12d3-a456-426614174001",
          "constraints": {
            "required": true,
            "format": "uuid"
          }
        },
        {
          "name": "customerId",
          "dataType": "string",
          "isPrimitive": true,
          "description": "The unique identifier of the customer who placed the order.",
          "example": "123e4567-e89b-12d3-a456-426614174000",
          "constraints": {
            "required": true,
            "format": "uuid"
          }
        },
        {
          "name": "orderDate",
          "dataType": "string",
          "isPrimitive": true,
          "description": "The date and time when the order was placed.",
          "example": "2024-12-06T09:00:00Z",
          "constraints": {
            "required": true,
            "format": "date-time"
          }
        },
        {
          "name": "status",
          "dataType": "string",
          "isPrimitive": true,
          "description": "The current status of the order.",
          "example": "Pending",
          "constraints": {
            "required": true,
            "enum": ["Pending", "Processing", "Shipped", "Delivered", "Cancelled"]
          }
        },
        {
          "name": "items",
          "dataType": "array",
          "isPrimitive": false,
          "description": "A list of items included in the order.",
          "example": [
            {
              "productId": "abc123",
              "quantity": 2,
              "price": 19.99
            }
          ],
          "constraints": {
            "required": true,
            "minItems": 1
          }
        }
      ]
    }
  ]
}
"""


