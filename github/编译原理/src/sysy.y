%{
#include <stdio.h>
#include <ast/ast.h>
void yyerror(const char *s);
extern int yylex(void);
extern NodePtr root;
root = new TreeRoot()
%}

/// types
%union {
    int ival;
    char *ident;
    ExprPtr expr;
    OpType op;
}

%token <ival> INTCONST
%token <ident> IDENT
%token ADD SUB MUL DIV ASSIGN GT GE LT LE EQ NEQ MOD AND OR NOT 
%token IF ELSE WHILE BREAK RETURN TRUE FALSE VOID INT
%token LPAREN RPAREN LBRACKET RBRACKET LBRACE RBRACE COMMA SEMICOLON

%start CompUnit

%type <expr> CompUnit Decl BType VarDecl VarDefList VarDef ArrayIndexList InitVal
%type <expr> InitValList FuncDef FuncFParams FuncFParam NumList Block BlockItemList BlockItem
%type <expr> Stmt Exp Cond LVal ExpList1 PrimaryExp Number UnaryExp UnaryOp FuncRParams ExpList2
%type <expr> MulExp AddExp RelExp EqExp LOrExp LAndExp

%left AND OR
%left ASSIGN GT GE LT LE EQ NEQ
%left ADD SUB 
%left MUL DIV MOD
%left NOT

%%
CompUnit : Exp {root = $1; }
Exp : Term { $$ = $1;}
    | SUB Exp %prec UMINUS { $$ = new TreeUnaryExpr(OP_Neg, $2); }
    | ADD Exp %prec UPLUS  { $$ = $2; }
    | Exp ADD Exp { $$ = new TreeBinaryExpr(OpType::OP_Add, $1, $3); }
    | Exp SUB Exp { $$ = new TreeBinaryExpr(OpType::OP_Sub, $1, $3); }
    | Exp MUL Exp { $$ = new TreeBinaryExpr(OpType::OP_Mul, $1, $3); }
    | Exp DIV Exp { $$ = new TreeBinaryExpr(OpType::OP_Div, $1, $3); }
    ;

Term : INT { $$ = new TreeIntegerLiteral($1); }
     ;
%%
CompUnit    : Decl                                      { root->children.push_back($1); }
            | FuncDef                                   { root->children.push_back($1); }
            | CompUnit Decl                             { root->children.push_back($2); }
            | CompUnit FuncDef                          { root->children.push_back($2); }
            ;

BType       : INT                                       { $$ = new TreeTypeCast(CastType::Int); }
            ;

Decl        : VarDecl                                   { $$ = $1; }
            ;

VarDecl         : BType VarDefList SEMICOLON                    { $$ = new AST("VarDecl"); $$->child = $2->child; }
                ;

VarDefList  : VarDefList COMMA VarDef                           { $$ = $1; $$->insert($3); }
            | VarDef                                            { $$ = new AST("temp"); $$->insert($1); }
            ;

VarDef          : IDENT                                             { $$ = new TreeStringLiteral($1); }
                | IDENT ASSIGN InitVal                              { $$ = new TreeStringLiteral($1,$3); }
                | IDENT ArrayIndexList                              { $$ = new TreeStringLiteral($1); }
                | IDENT ArrayIndexList ASSIGN InitVal               { $$ = new TreeStringLiteral($1,$4); }
                ;

ArrayIndexList : ArrayIndexList LBRACKET INTCONST RBRACKET                     { $$ = $1; $$->insert(new AST("IntConst",$3)); }
                | LBRACKET INTCONST RBRACKET                                   { $$ = new AST("test"); $$->insert(new AST("IntConst",$2)); }
                ;

InitVal : Exp                                                   { $$ = $1; }
        | LBRACE RBRACE                                         { $$ = new AST("test"); }
        | LBRACE InitValList RBRACE                             { $$ = new AST("test"); $$->insert($2); }
        ;

InitValList : InitValList COMMA InitVal                         { $$ = $1; $$->insert($3); }
            | InitVal                                           { $$ = new AST("test"); $$->insert($1); }
            ;

FuncDef : BType IDENT LPAREN RPAREN Block                       { $$ = new AST("FuncDef"); $$->dtype = $1->tokentype; $$->ID = $2; $$->insert($5); }
        | BType IDENT LPAREN FuncFParams RPAREN Block           { $$ = new AST("FuncDef"); $$->dtype = $1->tokentype; $$->ID = $2; $$->child = $4->child; $$->insert($6); }
        ;

FuncFParams     : FuncFParams COMMA FuncFParam                  { $$ = $1; $$->insert($3); if (SYNTAXTESTPRINT) { printf("[FuncFParams->FuncFParamList COMMA FuncFParam] \n"); } }
                | FuncFParam                                    { $$ = new AST("temp"); $$->insert($1); if (SYNTAXTESTPRINT) { printf("[FuncFParams->FuncFParam] \n"); } }
                ;

FuncFParam      : INT IDENT                                   { $$ = new AST("FuncFParam"); $$->ID = $2; if (SYNTAXTESTPRINT) { printf("[FuncFParam->BType IDENT] \n"); } }
                | INT IDENT NumList                           { $$ = new AST("FuncFParam"); $$->ID = $2; $$->insert($3); if (SYNTAXTESTPRINT) { printf("[FuncFParam->BType IDENT LBRACKET RBRACKET NumList] \n"); } }
                ;

NumList : NumList LBRACKET INTCONST RBRACKET        { $$ = $1; $$->insert(new AST("IntConst", $3)); if (SYNTAXTESTPRINT) { printf("[NumList->NumList LBRACKET INTCONST RBRACKET] \n"); } }
        | LBRACKET RBRACKET                         { $$ = new AST("test"); if (SYNTAXTESTPRINT) { printf("[NumList->LBRACKET RBRACKET] \n"); } }
        ;

Block           : LBRACE RBRACE                         { $$ = new AST("Block"); if (SYNTAXTESTPRINT) { printf("[Block->LBRACE RBRACE] \n"); } }
                | LBRACE BlockItemList RBRACE           { $$ = new AST("Block"); $$->child = $2->child; if (SYNTAXTESTPRINT) { printf("[Block->LBRACE BlockItemList RBRACE] \n"); } }
                ;

BlockItemList   : BlockItemList BlockItem               { $$ = $1; $$->insert($2); if (SYNTAXTESTPRINT) { printf("[BlockItemList->BlockItemList BlockItem] \n"); } }
                | BlockItem                             { $$ = new AST("temp"); $$->insert($1); if (SYNTAXTESTPRINT) { printf("[BlockItemList->BlockItem] \n"); } }
                ;

BlockItem       : Decl                                  { $$ = $1; if (SYNTAXTESTPRINT) { printf("[BlockItem->Decl] \n"); } }
                | Stmt                                  { $$ = $1; if (SYNTAXTESTPRINT) { printf("[BlockItem->Stmt] \n"); } }
                ;

Stmt            : LVal ASSIGN Exp SEMICOLON                     { $$ = new AST("AssignStmt"); $$->insert($1); $$->insert($3); }
                | SEMICOLON                                     { $$ = new AST("NullStmt"); }
                | Exp SEMICOLON                                 { $$ = $1; $$->tokentype = "ExpStmt"; }
                | Block                                         { $$ = $1; $$->tokentype = "Block"; }
                | IF LPAREN Cond RPAREN Stmt ELSE Stmt          { $$ = new AST("IfStmt"); $$->insert($3); $$->insert($5); $$->insert($7); }
                | IF LPAREN Cond RPAREN Stmt                    { $$ = new AST("IfStmt"); $$->insert($3); $$->insert($5); }
                | WHILE LPAREN Cond RPAREN Stmt                 { $$ = new AST("WhileStmt"); $$->insert($3);$$->insert($5); }
                | RETURN SEMICOLON                              { $$ = new AST("ReturnStmt"); }
                | RETURN Exp SEMICOLON                          { $$ = new AST("ReturnStmt"); $$->insert($2); }
                ;

Exp : AddExp                                            { $$ = $1; }
    ;

Cond            : LOrExp                                { $$ = $1; }
                ;

LVal            : IDENT                                 { $$ = new AST("Ident"); $$->ID = $1; if (SYNTAXTESTPRINT) { printf("[LVal->IDENT] \n"); } }
                | IDENT ExpList1                        { $$ = new AST("Ident"); $$->ID = $1; $$->child = $2->child; if (SYNTAXTESTPRINT) { printf("[LVal->IDENT ExpList1] \n"); } }
                ;

ExpList1        : ExpList1 LBRACKET Exp RBRACKET    { $$ = $1; $$->insert($3); if (SYNTAXTESTPRINT) { printf("[ExpList1->ExpList1 LBRACKET Exp RBRACKET] \n"); } }
                | LBRACKET Exp RBRACKET             { $$ = new AST("test"); $$->insert($2); if (SYNTAXTESTPRINT) { printf("[ExpList1->LBRACKET Exp RBRACKET] \n"); } }
                ;

PrimaryExp      : LPAREN Exp RPAREN                     { $$ = $2; if (SYNTAXTESTPRINT) { printf("[PrimaryExp->LPAREN Exp RPAREN] \n"); } }
                | LVal                                  { $$ = $1; if (SYNTAXTESTPRINT) { printf("[PrimaryExp->LVal] \n"); } }
                | Number                                { $$ = $1; if (SYNTAXTESTPRINT) { printf("[PrimaryExp->Number] \n"); } }
                ;

Number          : INTCONST                              { $$ = new AST("IntConst", $1); }
                ;

UnaryExp        : PrimaryExp                            { $$ = $1; }
                | IDENT LPAREN RPAREN                   { $$ = new AST("Call"); $$->ID = $1; }
                | IDENT LPAREN FuncRParams RPAREN       { $$ = new AST("Call"); $$->ID = $1; $$->insert($3); }
                | UnaryOp UnaryExp                      { $$ = new AST("test"); $$->insert($1); $$->insert($2); }
                ;

UnaryOp         : ADD                                   { $$ = $1; }
                | SUB                                   { $$ = $1; }
                | NOT                                   { $$ = $1; }
                ;

FuncRParams     : ExpList2                              { $$ = $1; }
                ;

ExpList2        : ExpList2 COMMA Exp                    { $$ = $1; $$->insert($3); }
                | Exp                                   { $$ = new AST("ParmVarDecl"); $$->insert($1); }
                ;

MulExp          : UnaryExp                              { $$ = $1; }
                | MulExp MUL UnaryExp                   { $$ = new TreeBinaryExpr(OpType::OP_Mul, $1, $3); }
                | MulExp DIV UnaryExp                   { $$ = new TreeBinaryExpr(OpType::OP_Div, $1, $3); }
                | MulExp MOD UnaryExp                   { $$ = new TreeBinaryExpr(OpType::OP_Mod, $1, $3); }
                ;

AddExp          : MulExp                                { $$ = $1; }
                | AddExp ADD MulExp                     { $$ = new TreeBinaryExpr(OpType::OP_Add, $1, $3); }
                | AddExp SUB MulExp                     { $$ = new TreeBinaryExpr(OpType::OP_Sub, $1, $3); }
                ;

RelExp          : AddExp                                { $$ = $1; }
                | RelExp GT AddExp                      { $$ = new TreeBinaryExpr(OpType::OP_Gt, $1, $3); }
                | RelExp GE AddExp                      { $$ = new TreeBinaryExpr(OpType::OP_Ge, $1, $3); }
                | RelExp LT AddExp                      { $$ = new TreeBinaryExpr(OpType::OP_Lt, $1, $3); }
                | RelExp LE AddExp                      { $$ = new TreeBinaryExpr(OpType::OP_Le, $1, $3); }
                ;

EqExp           : RelExp                                { $$ = $1; }
                | EqExp EQ RelExp                       { $$ = new TreeBinaryExpr(OpType::OP_Eq, $1, $3); }
                | EqExp NEQ RelExp                      { $$ = new TreeBinaryExpr(OpType::OP_Ne, $1, $3); }
                ;

LAndExp         : EqExp                                 { $$ = $1; }
                | LAndExp AND EqExp                     { $$ = new TreeBinaryExpr(OpType::OP_Land, $1, $3); }
                ;

LOrExp          : LAndExp                               { $$ = $1; }
                | LOrExp OR LAndExp                     { $$ = new TreeBinaryExpr(OpType::OP_Lor, $1, $3); }
                ;
%%
%%

void yyerror(const char *s) {
    printf("error: %s\n", s);
}
