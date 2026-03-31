/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

/**
 *
 * @author X1 G7
 */
import java.util.*;

public class Chess {
    
    // determine the peiece for each p,ayer
    private static final String player1Pieces = "PRHBKQ";
    private static final String player2Pieces = "prhbkq";
    private static boolean isCheck= false;
    public static boolean isGameRunning = true;
    private static boolean whiteTurn;// check who's turn is it
    private static char lastCapturedPiece = ' '; // stores the piece captured in the last move for undo
    
    
    public static void main(String[] args) {
        
        //inititalize board and print it
        char[][] board = new char[8][8];
        initializeBoard(board);
        printBoard(board);
        
        String move;
        //boolean isGameRunning = true;
        
        //start the game
        try (Scanner input = new Scanner(System.in)) {
            
            String gameStartEnd="";
            while(true){
                System.out.print("C to continue, exit to end the game: ");
                gameStartEnd=input.nextLine();
                if(gameStartEnd.equals("exit")){
                    break;
                }
                System.out.print("Enter player for white pieces: ");
                String player1Name=input.nextLine();
                System.out.print("Enter player for black pieces: ");
                String player2Name=input.nextLine();

                //boolean isGameRunning = true;
                String lastmove="";
                int count=0;

                //loop runs till game ends
                while (isGameRunning) {

                    boolean validMove;

                    // Player 1 move
                    do {
                        whiteTurn=true;

                        //check if the king is checkmate or not 
                        if(isCheckmate(board, player1Pieces)) {
                            System.out.println("");
                            System.out.println("CHECKMATE!! "+ player2Name +" Wins.");

                            //if king is checkmate ends the game
                            isGameRunning = false;
                            break;
                        }
                        // if king is not check then the game proceeds normally
                        isCheck=false;

                        //User Input move
                        System.out.println(player1Name+"'s Turn: ");
                        System.out.print("enter move (e.g., 'e2 e4') or 'quit' to exit or 'undo':  ");

                        move = input.nextLine();
                        System.out.println("");
                        if (move.equalsIgnoreCase("quit")) {

                            isGameRunning = false;
                            break;

                        }

                        if(move.equalsIgnoreCase("undo")) {
                            if(!(count > 0)){
                                System.out.println("You cannot undo first move");
                            }
                            else{
                                undoMove(board,lastmove);
                                printInvertedBoard(board);
                                break;
                            }
                        }

                        //calls valid move method to check for validity
                        validMove = makeMove(board, move, player1Pieces);
                        if (validMove) {// if move is valid then print 
                            printInvertedBoard(board);
                            count++;
                            lastmove=move;
                        } else if (isGameRunning && !(move.equalsIgnoreCase("quit"))) {//check if the game is running
                            if(!isCheck)
                                System.out.println("Invalid move. Try again.");
                        }

                    } while (!validMove);// loops keep running till the move is done

                    //if the user typed quit
                    if (!isGameRunning) {
                        System.out.println("Thanks for playing");
                        return;
                    }

                    // Player 2 move
                    do {//loop runs till the mve is valid and performed

                        whiteTurn = false;

                        //checks if the king is checkmate
                        if(isCheckmate(board, player2Pieces)) {
                            System.out.println(" ");
                            System.out.println("CHECKMATE!! "+player1Name+" Wins.");

                            //if king is checkmate then the game ends
                            isGameRunning = false;
                            break;
                        }

                        isCheck=false; 

                        //input move for player 2
                        System.out.println(player2Name+"'s Turn: ");
                        System.out.print("Enter move (e.g., 'e7 e5') or 'quit' to exit or 'undo': ");
                        move = input.nextLine();
                        if (move.equalsIgnoreCase("quit")) {
                            isGameRunning = false;
                            break;
                        }

                        if(move.equalsIgnoreCase("undo")) {
                            undoMove(board,lastmove);
                            printBoard(board);
                            break;
                        }

                        //check if the move is valid
                        validMove = makeMove(board, move,player2Pieces);
                        if (validMove) {
                            printBoard(board);
                            lastmove=move;
                        } else if (isGameRunning) {//cif the move is invalid then check if game runs
                            if(!isCheck)//if king is not checking then print this
                                System.out.println("Invalid move. Try again.");
                        }
                    } while (!validMove);

                    if (!isGameRunning) {
                        System.out.println("Thanks for playing");
                        break;
                    }
                }
            }
            
        }
    }
     
    
    
    // makes the board
    public static void initializeBoard(char[][] board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 != 0) {
                    board[i][j] = '/';
                } else {
                    board[i][j] = ' ';
                }
            }
        }
        
        for (int i = 0; i < 8; i++) {
            board[1][i] = 'p'; // Black pawns
            board[6][i] = 'P'; // White pawns
        }

        
        board[0][0] = board[0][7] = 'r';//black rook
        board[7][0] = board[7][7] = 'R';//white rook
        board[0][1] = board[0][6] = 'h';//black Knight
        board[7][1] = board[7][6] = 'H';//White Knight
        board[0][2] = board[0][5] = 'b';//black Bishop
        board[7][2] = board[7][5] = 'B';//White Bishop
        board[0][3] = 'q';//black Queen
        board[7][4] = 'K';//White King
        board[0][4] = 'k';//Black King
        board[7][3] = 'Q';//white Queen
    }

    public static void printBoard(char[][] board) {
        //print alphatbets
        System.out.println("    a   b   c   d   e   f   g   h  ");
        for (int i = 0; i < 8; i++) {
            System.out.println("  +---+---+---+---+---+---+---+---+");
            System.out.print((8 - i) + " | ");//print row numbers
            for (int j = 0; j < 8; j++) {
                System.out.printf("%s | ", board[i][j]);//print board chars
            }
            System.out.println();
        }
        System.out.println("  +---+---+---+---+---+---+---+---+");
    }
    
    //invert the boaard
    public static void printInvertedBoard(char[][] board) {
        System.out.println("    h   g   f   e   d   c   b   a  ");
        for (int i = 7; i >= 0; i--) {
            System.out.println("  +---+---+---+---+---+---+---+---+");
            System.out.print((8-i)+" | ");
            for (int j = 7; j >= 0; j--) {
                //print the pieces on the board, inverted
                System.out.printf("%s | ", board[i][j]);
            }
            System.out.println();
        }
        System.out.println("  +---+---+---+---+---+---+---+---+");
    }
    
    //make move for both players
    public static boolean makeMove(char[][] board, String move,String playerPieces) {
        try {
            
            //SPlit the move string in array
            String[] positions = move.split(" ");
            if (positions.length != 2) {
                return false;
            }

            //conver the move string into ASCII to get integer
            int startRow = 8 - (positions[0].charAt(1) - '0');
            int startCol = positions[0].charAt(0) - 'a';
            int endRow = 8 - (positions[1].charAt(1) - '0');
            int endCol = positions[1].charAt(0) - 'a';
            
            //check the piece and the end squre where the piece moves
            char piece = board[startRow][startCol];
            char endSquare=board[endRow][endCol];
            
            //check if then player is moving his own piece
            if (playerPieces.indexOf(piece) == -1) {
                if(!isCheck)
                    System.out.println("You can only move your own pieces.");
                return false;
            }

            //check if the player is capturing opponent piece
            if (playerPieces.indexOf(board[endRow][endCol]) != -1) {
                if(!isCheck)
                    System.out.println("You cannot capture your own piece.");
                return false;
            }
            
            //check if the move is valid
            if (!isValidMove(board, startRow, startCol, endRow, endCol,playerPieces)) {
                return false;
            }

            //make the move
            board[endRow][endCol] = board[startRow][startCol];
            board[startRow][startCol] = ((startRow + startCol) % 2 == 0) ? ' ' : '/';
            
            //if the move gets the is made and it endangers the king the move is undo'ed
            if (isKingInCheck(board)){
                undoMove(board, startRow, startCol, endRow, endCol, piece , endSquare );
                return false;
            }
                
            //if nothing is wrong move is accepted
            lastCapturedPiece = endSquare;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    //undo the move
    public static void undoMove(char[][] board,int startRow, int startCol, int endRow, int endCol, char piece , char endSquare ){
        board[startRow][startCol] = piece;
        board[endRow][endCol] = endSquare;
    }
    
    public static void undoMove(char[][] board,String lastMove){
        //SPlit the move string in array
        String[] positions = lastMove.split(" ");
        if (!(positions.length != 2)) {
                
            //conver the move string into ASCII to get integer
            int startRow = 8 - (positions[0].charAt(1) - '0');
            int startCol = positions[0].charAt(0) - 'a';
            int endRow = 8 - (positions[1].charAt(1) - '0');
            int endCol = positions[1].charAt(0) - 'a';
                
            //undo the move
            board[startRow][startCol]= board[endRow][endCol];
            board[endRow][endCol]=lastCapturedPiece;
            
            // Revert lastCapturedPiece to effectively clear the undo history
            lastCapturedPiece = ((endRow + endCol) % 2 == 0) ? ' ' : '/';

        }
                
    }
            
    
    
    //check validity of a move for the piece you're trying to move
    public static boolean isValidMove(char[][] board, int startRow, int startCol, int endRow, int endCol,String playerPieces) {
        
        //on which piece the move is bieng made
        char piece = board[startRow][startCol];
        
        switch (piece) {
            
            case 'P':// White pawnstartRow, startCol, endRow, endCol, 'P'));
                return (isValidPawnMove(board, startRow, startCol, endRow, endCol, 'P',playerPieces));
            case 'p': // Black pawn
                return (isValidPawnMove(board, startRow, startCol, endRow, endCol, 'p',playerPieces));
            
            case 'R': // White rook
                return (isValidRookMove(board, startRow, startCol, endRow, endCol));
            case 'r': // Black rook
                    return (isValidRookMove(board, startRow, startCol, endRow, endCol));
                
            case 'B': // White bishop
                    return (isValidBishopMove(board, startRow, startCol, endRow, endCol));
            case 'b': // Black bishop
                    return (isValidBishopMove(board, startRow, startCol, endRow, endCol));
                
            case 'Q': // White bishop
                    return (isValidQueenMove(board, startRow, startCol, endRow, endCol));
            case 'q': // Black bishop
                    return (isValidQueenMove(board, startRow, startCol, endRow, endCol));
                
            case 'H': // White knight
                return (isValidKnightMove(startRow, startCol, endRow, endCol));
            case 'h': // Black knight
                    return isValidKnightMove(startRow, startCol, endRow, endCol);
            
            case 'K': // White bishop
                    return (isValidKingMove(board, startRow, startCol, endRow, endCol));
            case 'k': // Black bishop
                    return (isValidKingMove(board, startRow, startCol, endRow, endCol));
                 
                
            default:
                return false;
        }
    }

    public static boolean isValidPawnMove(char[][] board, int startRow, int startCol, int endRow, int endCol, char piece,String playerPieces) {
        
        int direction = (piece == 'P') ? -1 : 1; // White pawns move up (decrease row), black pawns move down (increase row)
        int startPosition = (piece == 'P') ? 6 : 1; // Starting row for white pawns is 6, for black pawns is 1

        int check=(startRow+direction);
        //check for single move 
        if (startCol == endCol && endRow == check && (board[endRow][endCol] == ' ' || board[endRow][endCol] == '/')) {
            return true;
        }
        
        //check for first double move
        int check2=(startRow + (2 * direction));
        
        if (startCol == endCol && startRow == startPosition && endRow == check2 &&
                (board[endRow][endCol] == ' ' || board[endRow][endCol] == '/' ) &&
                (board[check][startCol] == ' ' || board[check][startCol] == '/')) {
            return true;
        }
        
        // check for capture and return that boolean
        if (startCol != endCol && endRow == check && board[endRow][endCol] != ' '&&board[endRow][endCol] != '/'){
            return true;
        }
        
        if(!isCheck)
            System.out.print("Pawn only moves one block and captures only in cross, ");
        // if everything is false then false
        return false;
        
    }
    
    public static boolean isValidRookMove(char[][] board, int startRow, int startCol, int endRow, int endCol) {
        
        // Check if the move is diagonal
        if (startRow != endRow && startCol != endCol) {
            if(!isCheck)
                System.out.print("Rook only moves in cross, ");
            return false; // Rooks cannot move diagonally
        }

        // Check the path for the rook move
        if (startRow == endRow) {
            // Horizontal move
                int step = (endCol > startCol) ? 1 : -1;
                int allRows = (startCol+step);
                for (int i = allRows; i != endCol; i += step) {
                    if(i <= -1 || i >= 8)
                        return false;
                    if (board[startRow][i] != ' ' && board[startRow][i] != '/') {
                        if(!isCheck)
                            System.out.print("The path is not clear, ");
                        return false; // Path is not clear
                    }
                }
            
            
        } else {
            // Vertical move
            int step = (endRow > startRow) ? 1 : -1;
            int allCol =(startRow + step);
            
                for (int i = allCol; i != endRow; i += step) {
                    if(i <= -1 || i >= 8)
                        return false;
                    if (board[i][startCol] != ' ' && board[i][startCol] != '/') {
                        if(!isCheck)
                            System.out.print("The path is not clear, ");
                        return false; // Path is not clear
                    }
                }
            }
        
        return true; // The move is valid
    }
    
    
    public static boolean isValidBishopMove(char[][] board, int startRow, int startCol, int endRow, int endCol) {
        
        // Check if the move is diagonal
        if (Math.abs(startRow - endRow) != Math.abs(startCol - endCol)) {
            if(!isCheck)
                System.out.print("Bishop only moves diagonally, ");
            return false; // Bishops move diagonally
        }

        // Check the path for the bishop move
        int rowStep = (endRow > startRow) ? 1 : -1;
        int colStep = (endCol > startCol) ? 1 : -1;
        
        int rowChange=(startRow + rowStep);
        int colChange=(startCol + colStep);
        
        if(endCol < 8 && endCol > -1 && endRow <= 8 && endRow >= -1   ){
            for (int row = rowChange, col = colChange; row != endRow; row += rowStep, col += colStep) {
                if(row == -1 || row == 8 || col == -1 || col == 8)
                    return false;
                if (board[row][col] != ' ' && board[row][col] != '/') {
                    if(!isCheck)
                        System.out.print("The path is not clear, ");
                    return false; // Path is not clear
                }  
            }
        }
       
        
        return true; // The move is valid
    }
    
    
    public static boolean isValidQueenMove(char[][] board, int startRow, int startCol, int endRow, int endCol) {
        
         // Check for horizontal move
        if (startRow == endRow) {
            if(endCol <= 8 && endCol >= -1 ){
                int step = (endCol > startCol) ? 1 : -1;
                for (int col = startCol + step; col != endCol; col += step) {
                    if( col == -1 || col == 8)
                        return false;
                    
                    if (board[startRow][col] != ' ' && board[startRow][col] != '/') {
                        if(!isCheck)
                            System.out.print("The path is not clear, ");
                        return false; // Path is not clear
                    }
                }
            }
        }
        
        // Check for vertical move
        else if (startCol == endCol) {
            if(endRow <= 8 && endRow >= -1 ){
                int step = (endRow > startRow) ? 1 : -1;
                for (int row = startRow + step; row != endRow; row += step) {
                    
                    if(row == -1 || row == 8 )
                        return false;
                    if (board[row][startCol] != ' ' && board[row][startCol] != '/') {
                        if(!isCheck)
                            System.out.print("The path is not clear, ");
                        return false; // Path is not clear
                    }
                }
            }
        }
        
        // Check for diagonal move
        else if (Math.abs(startRow - endRow) == Math.abs(startCol - endCol)) {
            int rowStep = (endRow > startRow) ? 1 : -1;
            int colStep = (endCol > startCol) ? 1 : -1;
            
            if(endCol < 8 && endCol > -1 && endRow <= 8 && endRow >= -1   ){
                for (int row = (startRow + rowStep), col = (startCol + colStep); row != endRow; row += rowStep, col += colStep) {
                    
                    if(row == -1 || row == 8 || col == -1 || col == 8)
                        return false;
                    
                    if (board[row][col] != ' ' && board[row][col] != '/') {
                        if(!isCheck)
                            System.out.print("The path is not clear, ");
                        return false; // Path is not clear
                    }
                }
            }
        } else {
            if(!isCheck)
                System.out.println("A queen can only move horizontally, vertically, or diagonally.");
            return false;
        }
        return true; // The path is clear
    }
    
    public static boolean isValidKnightMove(int startRow, int startCol, int endRow, int endCol) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        //check movement
        if((rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1)){
            return true;
        }
        
        if(!isCheck)
            System.out.print("Not a valid move for knight, ");
        return false;
    }
    
    public static boolean isValidKingMove(char[][] board, int startRow, int startCol, int endRow, int endCol) {
        
        // King can move one square in any direction
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);
        if (rowDiff > 1 || colDiff > 1) {
            if(!isCheck)
                System.out.print("King can only move one square, ");
            return false; // Move is too far for the king
        }
  
        return true; // The move is valid
    }
    
    //find any char on the board
    public static int[] findCharacter(char[][] board, char piece){
        for (int row=0;row<board.length;row++){
            for(int col=0;col<board[row].length;col++){
                if (board[row][col] == piece){
                    return new int[]{row,col}; 
                }
            }
        }
        // the method never comes here because there is always a king in game.
        return new int[]{-1, -1};
    }
    
    
    public static boolean isKingInCheck(char[][] board) {
        
        char king = (whiteTurn) ? 'K' : 'k' ;
        int[] index = findCharacter(board,king);
        int kingRow , kingCol;
        kingRow=index[0];
        kingCol=index[1];
        
        
        // Check for threats from pawns
        if (isPawnThreateningKing(board, kingRow, kingCol, king)){
            if(!isCheck)
                System.out.print("Enemy Pawn checks King");
            return true;
        }

        // Check for threats from knights
        if (isKnightThreateningKing(board, kingRow, kingCol, king)){
            if(!isCheck)
                System.out.print("Enemy Knight checks King");
            return true;
        }

        // Check for threats from bishops
        if (isBishopThreateningKing(board, kingRow, kingCol, king)){
            if(!isCheck)
                System.out.print("Enemy Bishop or queen Threats King");
            return true;
        }

        // Check for threats from rooks
        if (isRookThreateningKing(board, kingRow, kingCol, king)){
            if(!isCheck)
                System.out.print("Enemy Rook or queen Threatens King");
            return true;
        }

        // Check for threats from the opposing king
        if (isKingThreateningKing(board, kingRow, kingCol, king)){
            if(!isCheck)
                System.out.print("Opponent King checks King");
            return true;
        }

        // If none of the pieces can attack the king, then the king is not in check
        return false;
    }

    //check if pawn threatens king
    public static boolean isPawnThreateningKing(char[][] board, int kingRow, int kingCol, char king) {
        
        // Determine the direction based on the color of the king (white or black)
        int pawnDirection = (king == 'K') ? 1 : -1; // White king moves up, black king moves down

        // Check if there's a pawn diagonally in front of the king
        int leftDiagonalRow = kingRow + pawnDirection;
        int leftDiagonalCol = kingCol - 1;
        int rightDiagonalRow = kingRow + pawnDirection;
        int rightDiagonalCol = kingCol + 1;

        // Check left diagonal
        if (leftDiagonalRow >= 0 && leftDiagonalRow < 8 && leftDiagonalCol >= 0 && leftDiagonalCol < 8) {
            //check the charachter 
            char leftDiagonalPiece = board[leftDiagonalRow][leftDiagonalCol];
            // check if there's a pawn on diagonal
            if (leftDiagonalPiece == 'p' || leftDiagonalPiece == 'P') {
                return true; // Pawn threatens the king
            }
        }

        // Check right diagonal
        if (rightDiagonalRow >= 0 && rightDiagonalRow < 8 && rightDiagonalCol >= 0 && rightDiagonalCol < 8) {
            // saves the charachter on the diagonal
            char rightDiagonalPiece = board[rightDiagonalRow][rightDiagonalCol];
            //checks if there's a pawn on diagonal
            if (rightDiagonalPiece == 'p' || rightDiagonalPiece == 'P') {
                return true; // Pawn threatens the king
            }
        }

        return false; // No pawn threatens the king
    }

    //checks of enemy knight threatens king
    public static boolean isKnightThreateningKing(char[][] board, int kingRow, int kingCol, char king) {
        // All possible L-shaped moves for a knight relative to its position
        int[][] knightMoves = {
            {-2, -1}, {-1, -2}, {1, -2}, {2, -1},
            {-2, 1}, {-1, 2}, {1, 2}, {2, 1}
        };

        for (int[] move : knightMoves) {
            int potentialRow = kingRow + move[0];
            int potentialCol = kingCol + move[1];

            // Check if the potential position is within the board boundaries
            if (potentialRow >= 0 && potentialRow < 8 && potentialCol >= 0 && potentialCol < 8) {
                char piece = board[potentialRow][potentialCol];
                // Check if there's a knight in any of the L-shaped positions
                if ((king=='K' && piece == 'h') || (king == 'k' && piece == 'H')) {
                    return true; // Knight is threatening the king
                }
            }
        }

        return false; // No knight is threatening the king
    }

    //check if Bishop or queen threatens king
    public static boolean isBishopThreateningKing(char[][] board, int kingRow, int kingCol, char king) {
        // Check all four diagonals for a threatening bishop
       int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

       for (int[] direction : directions) {
           int threatRow = kingRow + direction[0];
           int threatCol = kingCol + direction[1];

           //runs the loop until reach end of the board
           while (threatRow >= 0 && threatRow < 8 && threatCol >= 0 && threatCol < 8) {
               char piece = board[threatRow][threatCol];
               // If we encounter a piece, check if it's a bishop
               if (piece != ' ' && piece != '/') {
                   if ((king=='K' && (piece == 'b'|| piece == 'q')) || (king == 'k' &&(piece == 'B' || piece == 'Q' ))) {
                       return true; // Bishop is threatening the king
                   }
                   break; // Blocked by another piece
               }
               threatRow += direction[0];
               threatCol += direction[1];
           }
       }
       return false; // No bishop is threatening the king
   }
    
    //check if enemy rook or queen threatens the king
    public static boolean isRookThreateningKing(char[][] board, int kingRow, int kingCol, char king) {
        // Check horizontally (left and right)
        for (int col = 0; col < 8; col++) {
            if (col != kingCol) {
                char piece = board[kingRow][col];
                if ((king=='K' && (piece == 'r'|| piece == 'q')) || (king == 'k' &&(piece == 'R' || piece == 'Q' ))) {
                    return true; // Rook is threatening the king
                }
            }
        }

        // Check vertically (up and down)
        for (int row = 0; row < 8; row++) {
            if (row != kingRow) {
                char piece = board[row][kingCol];
                if ((king=='K' && (piece == 'r'|| piece == 'q')) || (king == 'k' &&(piece == 'R' || piece == 'Q' ))) {
                    return true; // Rook is threatening the king
                }
            }
        }

        return false; // No rook or queen is threatening the king
    }
    
    //check if enemy king threatens the king
    public static boolean isKingThreateningKing(char[][] board, int kingRow, int kingCol, char king) {
        
        // All possible adjacent positions around the king
        int[][] adjacentMoves = {
            {-1, -1}, {-1, 0}, {-1, 1}, {0, -1},{0, 1},{1, -1}, {1, 0},  {1, 1}
        };

        for (int[] move : adjacentMoves) {
            int newRow = kingRow + move[0];
            int newCol = kingCol + move[1];

            // Check if the potential position is within the board boundaries
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                char piece = board[newRow][newCol];
                // Check if there's an enemy king in any of the adjacent positions
                if ((king == 'K' && piece == 'k') || (king == 'k' && piece == 'K')) {
                    return true; // Enemy king is threatening the king
                }
            }
        }
        return false;
    }
    
    public static boolean isCheckmate(char[][] board, String playerPieces) {
        // First, check if the king is in check
        if (!isKingInCheck(board)) {
            return false;
        }

        isCheck=true;
        // Try all possible moves for all pieces
        for (int startRow = 0; startRow < board.length; startRow++) {
            for (int startCol = 0; startCol < board[startRow].length; startCol++) {
                // If it's not a piece of the current player, skip it
                if (playerPieces.indexOf(board[startRow][startCol]) == -1) {
                    continue;
                }

                for (int endRow = 0; endRow < board.length; endRow++) {
                    for (int endCol = 0; endCol < board[endRow].length; endCol++) {
                        if (startRow == endRow && startCol == endCol) continue;
                        if (playerPieces.indexOf(board[endRow][endCol]) != -1) continue;
                        
                        // Check if the move is valid
                        if (isValidMove(board, startRow, startCol, endRow, endCol, playerPieces)) {
                            char piece=board[startRow][startCol];
                            char endSquare=board[endRow][endCol];
                            
                            // make move temporarily
                            board[endRow][endCol]=piece;
                            board[startRow][startCol]=((startRow + startCol) % 2 == 0) ? ' ' : '/';
                            
                            boolean remainsInCheck = isKingInCheck(board);
                            
                            // undo move
                            board[startRow][startCol] = piece;
                            board[endRow][endCol] = endSquare;
                            
                            if(!remainsInCheck){
                                isCheck = false; // Reset the checking flag
                                return false; // Found a valid move to escape check!
                            }
                        }
                    }
                }
            }
        }
        
        // If no moves can be made to escape check, it's checkmate
        return true;
   
    }
    
    //to copy the board
    /*private static char[][] copyBoard(char[][] board) {
        char[][] copy = new char[board.length][];
        for (int i = 0; i < board.length; i++) {
            copy[i] = board[i].clone();
        }
        return copy;
    }*/
     
}
