# Guide de Validation des Entités

Ce fichier centralise les validations appliquées à chaque entité dans le projet. Les annotations sont utilisées pour garantir l'intégrité des données et la conformité des valeurs avant la persistance dans la base de données.

## Table des Matières
1. [Client](#client)
2. [LigneCommandeClient](#lignecommandeclient)
3. [LigneCommandeFournisseur](#lignecommandefournisseur)
4. [LigneVente](#lignevente)
5. [MvtStk](#mvtstk)
6. [Utilisateur](#utilisateur)
7. [Roles](#roles)
8. [Ventes](#ventes)
9. [Category](#category)
10. [Adresse](#adresse)
11. [Entreprise](#entreprise)

## Client

- **Validations :**
    - Vérification de la présence du nom.
    - Vérification du format d'email.
    - Vérification de la validité du numéro de téléphone.

## LigneCommandeClient

- **Validations :**
    - Vérification de la quantité de produit (doit être supérieur à 0).
    - Vérification du prix (doit être un nombre positif).

## LigneCommandeFournisseur

- **Validations :**
    - Vérification de la quantité commandée (doit être supérieur à 0).
    - Vérification du prix unitaire (doit être un nombre positif).

## LigneVente

- **Validations :**
    - Vérification de la quantité vendue (doit être supérieure à 0).
    - Vérification du montant de la vente (doit être un nombre positif).

## MvtStk

- **Validations :**
    - Vérification de la quantité de mouvement (doit être supérieure à 0).
    - Vérification de la date du mouvement (doit être une date valide).

## Utilisateur

- **Validations :**
    - Vérification de la présence du nom d'utilisateur.
    - Vérification de la longueur du mot de passe (minimum 8 caractères).
    - Vérification de l'email au format valide.

## Roles

- **Validations :**
    - Vérification de l'existence du rôle dans la base de données.

## Ventes

- **Validations :**
    - Vérification de la date de vente (doit être une date valide).
    - Vérification de la validité des produits dans la vente.

## Category

- **Validations :**
    - Vérification de la présence du nom de la catégorie.
    - Vérification de la longueur du nom (ne doit pas dépasser 50 caractères).

## Adresse

- **Validations :**
    - Vérification de la présence de l'adresse.
    - Vérification du format du code postal.
    - Vérification de la validité de la ville.

## Entreprise

- **Validations :**
    - Vérification de la présence du nom de l'entreprise.
    - Vérification de l'existence de l'adresse.
    - Vérification du format du numéro de téléphone.
---

## Client

### Validation des Champs

```java
@NotBlank(message = "Le nom du client est obligatoire")
@Size(min = 5, max = 100, message = "Le nom doit être entre 5 et 100 caractères")
private String nom;

@NotBlank(message = "Le prénom du client est obligatoire")
@Size(min = 4, max = 100, message = "Le prénom doit être entre 4 et 100 caractères")
private String prenom;

@Email(message = "L'adresse email est invalide")
@NotBlank(message = "L'Email du client est obligatoire")
private String mail;

@Pattern(regexp = "^[0-9+\\s().-]{6,20}$", message = "Le numéro de téléphone doit contenir entre 6 et 20 caractères et peut inclure des chiffres, espaces, +, (), ou -")
private String numTel;
```

# Explication

## @NotBlank
Empêche la soumission de champs vides.

## @Size
Définit la taille minimale et maximale des chaînes de caractères.

## @Email
Vérifie que l'adresse email a un format valide.

## @Pattern
Assure que le numéro de téléphone respecte un format spécifique.
---

# LigneCommandeClient

## Validation des Champs

```java
@NotNull(message = "La quantité est obligatoire")
@DecimalMin(value = "0.01", message = "La quantité doit être supérieure à zéro")
private BigDecimal quantite;

@NotNull(message = "Le prix unitaire est obligatoire")
@DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à zéro")
private BigDecimal prixUnitaire;
```

# Explication

## @NotBlank
Empêche la soumission de champs vides.

## @Size
@DecimalMin : Assure que les valeurs des champs soient supérieures à un seuil minimal (ici 0.01).

---
## LigneCommandeFournisseur

### Validation des Champs

```java
@NotNull(message = "La quantité est obligatoire")
@DecimalMin(value = "0.01", message = "La quantité doit être supérieure à zéro")
private BigDecimal quantite;

@NotNull(message = "Le prix unitaire est obligatoire")
@DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à zéro")
private BigDecimal prixUnitaire;

@NotNull(message = "L'entreprise est obligatoire")
private Long entrepriseId;
```
# Explication

## @NotNull
Garantit que les champs quantite, prixUnitaire et entrepriseId sont renseignés.

---
## LigneVente

### Validation des Champs

```java
@NotNull(message = "La quantité est obligatoire")
@DecimalMin(value = "0.01", message = "La quantité doit être supérieure à zéro")
private BigDecimal quantite;

@NotNull(message = "Le prix unitaire est obligatoire")
@DecimalMin(value = "0.01", message = "Le prix unitaire doit être supérieur à zéro")
private BigDecimal prixUnitaire;
```

---

## Exemple de Validation des Entités

### Quantité

```java
@NotNull(message = "La quantité est obligatoire")
@DecimalMin(value = "0.01", message = "La quantité doit être supérieure à zéro")
private BigDecimal quantite;
```

---
## Utilisateur

### Validation des Champs

```java
@NotBlank(message = "Le nom est obligatoire")
@Size(min = 2, max = 50, message = "Le nom doit être entre 2 et 50 caractères")
private String nom;

@NotBlank(message = "Le prénom est obligatoire")
@Size(min = 2, max = 50, message = "Le prénom doit être entre 2 et 50 caractères")
private String prenom;

@Email(message = "L'email doit être valide")
@NotBlank(message = "L'email est obligatoire")
private String email;

@NotNull(message = "La date de naissance est obligatoire")
private LocalDateTime dateDeNaissance;
```
---
## Roles

### Validation des Champs

```java
@NotBlank(message = "Le nom du rôle est obligatoire")
@Size(min = 3, max = 50, message = "Le nom du rôle doit être entre 3 et 50 caractères")
private String roleName;
```
---

## Vente
## Validation des Propriétés

```java
@NotBlank(message = "Le code est obligatoire")
@Size(min = 3, max = 50, message = "Le code de la vente doit être entre 3 et 50 caractères")
private String code;

@NotNull(message = "La date de vente est obligatoire")
private LocalDateTime dateVente;
```
---

## Category

### Validation des Champs

```java
@NotBlank(message = "La désignation est obligatoire")
@Size(min = 3, max = 100, message = "La désignation doit être entre 3 et 100 caractères")
private String designation;

@NotBlank(message = "Le code est obligatoire")
@Size(min = 2, max = 20, message = "Le code doit être entre 2 et 20 caractères")
private String code;
```
---

## Adresse

### Validation des Champs

```java
@NotBlank(message = "L'adresse 1 est obligatoire")
@Size(min = 5, max = 100, message = "L'adresse 1 doit contenir entre 5 et 100 caractères")
private String adresse1;

@Size(min = 4, max = 100, message = "L'adresse 2 doit contenir au maximum 100 caractères")
private String adresse2;

@NotBlank(message = "La ville est obligatoire")
@Size(min = 2, max = 50, message = "La ville doit contenir entre 2 et 50 caractères")
private String ville;

@NotBlank(message = "Le code postal est obligatoire")
@Size(min = 4, max = 10, message = "Le code postal doit contenir entre 4 et 10 caractères")
private String codePostale;

@NotBlank(message = "Le pays est obligatoire")
@Size(min = 2, max = 50, message = "Le pays doit contenir entre 2 et 50 caractères")
private String pays;
```
---

## Entreprise

### Validation des Champs

```java
@NotBlank(message = "Le nom de l'entreprise est obligatoire")
@Size(min = 5, max = 100, message = "Le nom doit contenir entre 5 et 100 caractères")
private String nom;

@NotBlank(message = "La description est obligatoire")
@Size(min = 5, max = 200, message = "La description doit contenir entre 5 et 200 caractères")
private String description;

@NotBlank(message = "Le code fiscal est obligatoire")
@Size(min = 5, max = 20, message = "Le code fiscal doit contenir entre 5 et 20 caractères")
private String codeFiscal;

@Email(message = "L'email doit être valide")
@NotBlank(message = "L'email est obligatoire")
private String email;

@NotBlank(message = "Le numéro de téléphone est obligatoire")
@Size(min = 10, max = 20, message = "Le numéro de téléphone doit contenir entre 10 et 20 caractères")
private String numTel;
```

---

## Conclusion

Ce guide centralise toutes les validations essentielles pour garantir que les données respectent les contraintes de l'application. Chaque entité et DTO a des règles strictes afin d'assurer la cohérence des données avant leur sauvegarde en base de données.
