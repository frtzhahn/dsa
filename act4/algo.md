# System Architecture & Logic Specification

## 1. System Startup & Initialization
1.1 **Clear Screen:** Detect operating system environment and invoke platform-specific terminal clear command (`cls` for Windows, `clear` for Unix-like systems) with ANSI escape sequence fallback.  
1.2 **Load Storage:** Initialize the record list by attempting to deserialize `database.ser` into memory via `ObjectInputStream`. If the file does not exist or is corrupted, initialize an empty list.  

## 2. Main Control Loop
2.1 **Display Interface:** Output the ASCII title banner and numerical menu choices (1 to 5).  
2.2 **Input Choice:** Read and validate the user selection between 1 and 5.  
2.3 **Route Operation:**
     **Option 1 (View Records):** Check if memory list is empty; if true, show error. Otherwise, print formatted spreadsheet table with 68-character width borders and column-aligned fields (ID: 7, Name: 30, Qty: 8, Price: 10).  
     **Option 2 (Add Record):** Read and validate unique ID ($\le 7$ characters, non-empty, case-insensitive uniqueness check). Read Name ($\le 30$ characters; fallback to "Unnamed Item" if empty). Read Quantity ($0 \le \text{qty} \le 99999$). Read Price ($0.00 \le \text{price} \le 9999999.99$). Instantiate new Data object and append to the list.  
     **Option 3 (Update Record):** Prompt for target ID and query list. If not found, display error and return to menu. Display target's current state via detailed metadata format. Prompt new Name ($\le 30$ characters; retain current value if blank). Prompt new Quantity ($0 \le \text{qty} \le 99999$) and Price ($0.00 \le \text{price} \le 9999999.99$). Overwrite object fields in memory.  
     **Option 4 (Delete Record):** Prompt for target ID. If found in list, remove target object; otherwise, display error.  
     **Option 5 (Save & Exit):** Serialize the active record list to `database.ser` using `ObjectOutputStream`, display confirmation, and terminate the program loop.  

## 3. Input Validation Subroutines
3.1 **Integer Validation:** Loop read operations until input parses successfully into a whole number within range $[min, max]$, catching `NumberFormatException`.  
3.2 **Decimal Validation:** Loop read operations until input parses successfully into a floating-point number within range $[min, max]$, catching `NumberFormatException`.

