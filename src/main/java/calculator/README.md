> This app is for educational purposes.
> 
# Project overview
A simple Java 21 calculator that runs in both console and GUI modes. 
It evaluates mathematical expressions with support for:

- Basic operators: +, -, *, /
- Exponentiation (e.g., ^, pown), square root
- Parentheses/brackets and negative numbers
- Error handling for invalid input

## How to run

Console:
>java -jar calculator.jar

GUI:
>java -jar calculator.jar --gui


# Project TODO and Roadmap

This document tracks completed work, decisions, and upcoming features for the calculator project.

## To Do

- [ ] Design, dark mode support, system theme detection

## Completed

- [x] Unit tests
- [x] Error handling
- [x] Comments
- [x] GUI
- [x] Understand the GUI and tests
- [x] Negative numbers support
- [x] Add negative and brackets support
- [x] Brackets and long equation support
- [x] Opening brackets error handling

## Not Needed

- [x] Operators array — not needed
- [x] Help panel for console — not needed

## Roadmap: Version 1.1

1. Display π symbol on the panel instead of “3.14159265” (parser should resolve the symbol).
2. Show math signs like square root and power with proper symbols on the panel; convert appropriately for the parser.
3. Display errors in the panel instead of separate windows.
4. Insert signs at the cursor position rather than always appending to the right.
5. If conflicts arise, remove the first, primitive calculator version.
6. Add trigonometric functions (sin, cos, tan).

### Implementation Note: Grid-Based Button Layout

This approach would save some lines, but I need to change every JButton to: JButton #number = (right side keep the same).

        for (int row = 0; row < 5; row++) {

            for (int col = 0; col < 4; col++) {

                gbc.gridx = col;

                gbc.gridy = row;

                gbc.gridwidth = 1;

                JButton btn = new JButton(); // set button text

                buttons.add(btn, gbc);
            }

        }
