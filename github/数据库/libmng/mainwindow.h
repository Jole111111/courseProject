#ifndef MAINWINDOW_H
#define MAINWINDOW_H
#include <QMainWindow>
#include <QFileDialog>
#include <QMessageBox>
#include <QtCore>
#include <QSqlQuery>
#include <QSqlError>
#include <QRadioButton>
#include <QString>
#include <QLayout>

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

extern bool IfLogin_as_admin;
extern bool IfLogin_as_user;

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();
    void getDatabase(QSqlDatabase db);

private slots:
    void on_SearchBooks_clicked();

    void on_AddBooks_clicked();

    void on_TokenImportBooks_clicked();

    void on_OktoAddBooks_clicked();

    void on_AddRows_clicked();

    void on_DelRows_clicked();

    void on_SearchinSelectedMode_clicked();

    void on_LoginButton_clicked();

    void on_Login_in_mode_clicked();

    void on_ExitLogin_clicked();

    void on_Admin_add_account_clicked();

    void on_Admin_del_account_clicked();

    void on_Modify_Admins_clicked();

    void on_LendBooks_clicked();

    void on_BooklendCard_id_confirm_clicked();

    void on_Book_id_to_lend_confirm_clicked();

    void on_Confirm_to_lendbook_clicked();

    void on_LandCard_clicked();
    
    void on_AddCards_add_clicked();
    
    void on_AddCards_del_clicked();
    
    void on_AddCards_confirm_clicked();
    
    void on_Confirm_to_del_card_clicked();

    void on_ReturnBooks_clicked();

    void on_Confirm_to_return_book_clicked();

private:
    Ui::MainWindow *ui;
    QSqlDatabase book_db;
};
#endif // MAINWINDOW_H
