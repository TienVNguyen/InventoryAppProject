# InventoryAppProject
An small demo app to complete the project 10. See the Tutorial on our cliffnotes for a step-by-step tutorial.

Time spent: 20 hours implementation (included searching) in total
 - 07/27: 4 hours
 - 07/28: 4 hours
 - 07/29: 4 hours
 - 07/30: 4 hours
 - 07/31: 4 hours
 
Implement link: https://github.com/TienVNguyen/InventoryAppProject

 - Layout:
 * [x] Overall Layout: The app contains a list of current products and a button to add a new product.
 * [x] List item layout: Each ListItem displays the product name, current quantity, and price. Each list item also allows the user to track a sale of the item
 * [x] Detail layout: The detail layout for each item displays the remainder of the information stored in the database.
 * [x] + The detail layout contains buttons to modify the current quantity either by tracking a sale or by receiving a shipment.
 * [x] + The detail layout contains a button to order from the supplier.
 * [x] + The detail view contains a button to delete the product record entirely.
 * [x] Layout Best Practices: The code adheres to all of the following best practices
 * [x] + Text sizes are defined in sp
 * [x] + Lengths are defined in dp
 * [x] + Padding and margin is used appropriately, such that the views are not crammed up against each other.
 * [x] Default Textview: When there is no information to display in the database, the layout displays a TextView with instructions on how to populate the database.

 - Functionality:
 * [x] Runtime Errors: The code runs without errors
 * [x] ListView Population: The listView populates with the current products stored in the table.
 * [x] Add product button: The Add product button prompts the user for information about the product and a picture, each of which are then properly stored in the table.
 * [x] Input validation: User input is validated. In particular, empty product information is not accepted.
 * [x] Sale button: The sale button on each list item properly reduces the quantity available by one, unless that would result in a negative quantity.
 * [x] Detail View intent: Clicking on the rest of each list item sends the user to the detail screen for the correct product.
 * [x] Modify quantity buttons: The modify quantity buttons in the detail view properly increase and decrease the quantity available for the correct product.
 * [x] + The student may also add input for how much to increase or decrease the quantity by.
 * [x] Order Button: The ‘order more’ button sends an intent to either a phone app or an email app to contact the supplier using the information stored in the database.
 * [x] Delete button: The delete button prompts the user for confirmation and, if confirmed, deletes the product record entirely and sends the user back to the main activity.
 * [x] External Libraries and Packages: The intent of this project is to give you practice writing raw Java code using the necessary classes provided by the Android framework; therefore, the use of external libraries for core functionality will not be permitted to complete this project.

 - Code Readability:
 * [x] Naming conventions : All variables, methods, and resource IDs are descriptively named such that another developer reading the code can easily understand their function.
 * [x] Formatting : The code is properly formatted i.e. there are no unnecessary blank lines; there are no unused variables or methods; there is no commented out code. The code also has proper indentation when defining variables and methods.
 

Notes:

Walkthrough of all functionalities:

![Video Walkthrough](inventory_app_project.gif)