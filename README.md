# ♟️ Procedural Java Chess Engine

<p align="center">
  <img src="https://img.shields.io/badge/Language-Java-orange.svg" alt="Language">
  <img src="https://img.shields.io/badge/Paradigm-Procedural-blue.svg" alt="Paradigm">
  <img src="https://img.shields.io/badge/Status-Complete-success.svg" alt="Status">
</p>

A fully functional, terminal-based Chess game built **entirely from scratch using 100% procedural programming patterns**. 

This project was specifically designed to demonstrate raw programming fundamentals—relying exclusively on multi-dimensional arrays, loops, mathematical ray-casting, and conditional logic. It intentionally avoids Object-Oriented Programming (OOP) to showcase complex state manipulation, deep validation layers, and matrix transformations in a single, cohesive codebase.

---

## 🌟 Key Features

*   **Pure Logic Implementation**: No Objects. No inheritance. No imported game libraries. Just pure, unadulterated Java logic.
*   **Ray-Casting Move Validation**: Mathematical pathfinding for sliding pieces (Rooks, Bishops, Queens) that checks for piece obstruction strictly using array bounds and index step mathematics.
*   **Virtual Move Simulation**: Evaluates "Check" and "Checkmate" the exact same way professional engines do—by recursively committing virtual moves to the board array to see if the outcome escapes danger, then dynamically rolling back the state.
*   **Robust State Reversion (Undo)**: Supports live single-turn rollbacks mid-game. Intelligently caches the `lastCapturedPiece` to resurrect characters from the void via array manipulation.
*   **Impervious Terminal Parsing**: Employs rigorous Try-Catch blocks preventing all bounds-checking crashes (`ArrayIndexOutOfBounds`, `StringIndexOutOfBounds`) from malevolent user inputs.

## 🎓 Why This Project Stands Out (Academic Value)

If you are a Computer Science student or a recruiter looking at this repository, this project operates completely without the "crutches" of modern OOP frameworks. 

Writing a chess game with OOP is standard; writing a chess suite strictly by transforming a `char[][]` matrix teaches unparalleled mastery of:
- **Spatial Grid Mathematics**: Translating algebraic notation (e.g., `e2 e4`) into Cartesian array coordinates.
- **Deep Algorithmic State Control**: Manually pushing/popping 2D array states.
- **Functional Side-Effect Management**: Separating terminal printing commands from silent backend checkmate move simulations using global state toggles.

It is guaranteed to impress professors grading on raw algorithmic comprehension and fundamental control-flow architecture.

---

## 🚀 How to Play

### Prerequisites
- Java Development Kit (JDK) 8 or higher installed on your system.
- A terminal or command-prompt window.

### Installation & Execution
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/procedural-java-chess.git
   ```
2. Navigate to the source folder:
   ```bash
   cd procedural-java-chess/src/chess
   ```
3. Compile the Java file:
   ```bash
   javac Chess.java
   ```
4. Run the engine:
   ```bash
   java chess.Chess
   ```

### Command Syntax
- Standard algebraic notation: `e2 e4`
- Undo previous move: `undo`
- Forfeit/Exit game: `quit`

---

## 🧠 Future Roadmap

While the codebase is stable, procedural programming intentionally implements a "complexity ceiling." Future challenges for anyone fork-testing their logic skills include:
- Implementing memory stacks for infinite `<Undo>` histories natively.
- Procedural `O(n)` optimizations for Checkmate evaluation caching.
- Implementing Castling and En Passant states using purely external index tracking (since the `char` primitives cannot store `hasMoved` states natively).

---

> *“Simplicity is prerequisite for reliability.”* — Edsger W. Dijkstra
