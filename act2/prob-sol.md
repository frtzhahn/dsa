# Problem
Create a program using one of the POS functions (with Inventory system).

# Solution
The program implements the checkout/billing function of a POS system integrated with an inventory management system. Products are pre-loaded into inventory with a code, name, price, and stock quantity, and new products can also be added through the menu. During checkout, items are added to a cart by entering the product code and quantity, with input validation to ensure the product code exists, the quantity is greater than 0, and enough stock is available before the item is accepted. The user types "done" to end item entry and proceed to billing. The program then computes the subtotal, applies a membership discount if applicable, adds a 12% tax, and calculates the grand total. It prompts for cash payment and computes the change, then displays a final receipt with the itemized list, subtotal, discount, tax, total, cash paid, and change.
