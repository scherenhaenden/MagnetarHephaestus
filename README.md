# MagnetarHephaestus

**MagnetarHephaestus** is a tool for generating and managing data models across different layers of an application. It supports **model creation, property mapping, constraints definition**, and export to multiple programming languages.

## Key Features

- **Model Layering**: Define models for various layers such as Business Logic, Data Access, etc.
- **Property Management**: Add detailed information for each property, including types, constraints, and descriptions.
- **Language Support**: Export models to multiple programming languages:
    - **C#**, **TypeScript**, **Kotlin**, **Python**, **Java**.
- **Mapping**: Easily map between different layers of your application.
- **Constraint Validation**: Define constraints like required fields, formats, min/max values, and enums.

## Example Input

MagnetarHephaestus accepts JSON definitions of your models. Here's an example:

```json
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
      "fields": [
        {
          "name": "id",
          "dataType": "string",
          "constraints": { "required": true, "format": "uuid" }
        },
        {
          "name": "email",
          "dataType": "string",
          "constraints": { "required": true, "format": "email" }
        }
      ]
    }
  ]
}
```

## How It Works

1. **Define Models**: Provide a JSON definition for your models.
2. **Generate Code**: MagnetarHephaestus generates language-specific code for the models.
3. **Export**: Save the generated models for your application.

### Output Example (C#)

```csharp
public class Customer
{
    [Required]
    [RegularExpression(@"^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$")]
    public string Id { get; set; }

    [Required]
    [EmailAddress]
    public string Email { get; set; }
}
```

### Output Example (Python)

```python
from pydantic import BaseModel, EmailStr, constr

class Customer(BaseModel):
    id: constr(regex=r"^[a-f0-9-]{36}$")
    email: EmailStr
```

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/scherenhaenden/MagnetarHephaestus.git
   cd MagnetarHephaestus
   ```

2. Build the project (e.g., with your preferred build tool).

## Usage

1. Run the program and provide your JSON model file.
2. Select the output language(s).
3. Save or copy the generated models into your project.

## Future Plans

- Extend support for additional languages and frameworks.
- Enhance model validation capabilities.
- Provide a web-based UI for easier model management.

