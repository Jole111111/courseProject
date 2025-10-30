#include <cstdint>
#include <type_traits>
#include <string>
#include <vector>

// 定义操作符类型枚举
enum OpType {
#define OpcodeDefine(x, s) x,
#include "common/common.def"
};

// 定义变量类型枚举
enum CastType {
#define CastTypeDefine(x, s) x,
#include "common/common.def"
};

// 定义节点类型枚举
enum NodeType {
#define TreeNodeDefine(x) x,
#include "common/common.def"
};

// 前置声明
struct Node;
using NodePtr = Node*;
struct TreeExpr;
using ExprPtr = TreeExpr*;
struct TreeType;

// 节点基类
struct Node {
    NodeType node_type;
    Node(NodeType type) : node_type(type) {}
    // 检查节点是否为特定类型
    template <typename T> bool is() {
        return node_type == std::remove_pointer_t<T>::this_type;
    }
    // 将节点转换为特定类型（安全转换）
    template <typename T> T as() {
        if (is<T>())
            return static_cast<T>(this);
        return nullptr;
    }
    // 将节点转换为特定类型（不安全转换）
    template <typename T> T as_unchecked() { return static_cast<T>(this); }
};

// 表达式节点基类
struct TreeExpr : public Node {
    TreeExpr(NodeType type) : Node(type) {}
};
// 二元表达式节点
struct TreeBinaryExpr : public TreeExpr {
    constexpr static NodeType this_type = ND_BinaryExpr;
    OpType op;
    ExprPtr lhs, rhs;
    TreeBinaryExpr(OpType op, ExprPtr lhs, ExprPtr rhs)
        : TreeExpr(this_type), op(op), lhs(lhs), rhs(rhs) {
    }
};
// 一元表达式节点
struct TreeUnaryExpr : public TreeExpr {
    constexpr static NodeType this_type = ND_UnaryExpr;
    OpType op;
    ExprPtr operand;
    TreeUnaryExpr(OpType op, ExprPtr operand)
        : TreeExpr(this_type), op(op), operand(operand) {
    }
};
// 整数字面量节点
struct TreeIntegerLiteral : public TreeExpr {
    constexpr static NodeType this_type = ND_IntegerLiteral;
    int64_t value;
    TreeIntegerLiteral(int64_t value) : TreeExpr(this_type), value(value) {}
};
// 字符串字面量节点
struct TreeStringLiteral : public TreeExpr {
    constexpr static NodeType this_type = ND_StringLiteral;
    std::string value;
    ExprPtr operand;
    TreeStringLiteral(std::string value) : TreeExpr(this_type), value(value) {}
    TreeStringLiteral(std::string value, ExprPtr operand) : TreeExpr(this_type), value(value) ,operand(operand){}
};
// 类型判断表达式节点
struct TreeTypeCast : public TreeExpr {
    constexpr static NodeType this_type = ND_TypeCast;
    CastType type;
    TreeTypeCast(CastType type)
        : TreeExpr(this_type), type(type) {
    }
};
// 根节点
struct TreeRoot : public Node {
    constexpr static NodeType this_type = ND_Root;
    std::vector<NodePtr> children;
    TreeRoot() : Node(this_type) {}
};
// 函数定义节点
struct FuncDef : public Node {
    constexpr static NodeType this_type = ND_FuncDef;
    std::string func_name;
    std::vector<std::string> param_types;
    std::string return_type;
    BlockPtr body;
    FuncDef(std::string name, std::vector<std::string> params, std::string ret_type, BlockPtr block)
        : Node(this_type), func_name(name), param_types(params), return_type(ret_type), body(block) {}
};
// 变量声明节点
struct VarDecl : public Node {
    constexpr static NodeType this_type = ND_VarDecl;
    std::string var_name;
    std::string var_type;
    ExprPtr init_value;
    VarDecl(std::string name, std::string type, ExprPtr init)
        : Node(this_type), var_name(name), var_type(type), init_value(init) {}
};
// 块节点
struct Block : public Node {
    constexpr static NodeType this_type = ND_Block;
    std::vector<NodePtr> statements;
    Block() : Node(this_type) {}
};

// 打印表达式节点的可能辅助函数
void print_expr(ExprPtr exp, std::string prefix = "", std::string ident = "");
