#include "loginwindow.h"
#include "ui_loginwindow.h"

LoginWindow::LoginWindow(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::LoginWindow)
{
    ui->setupUi(this);
    db = QSqlDatabase::addDatabase("QODBC");
}

LoginWindow::~LoginWindow()
{
    delete ui;
}

QSqlDatabase LoginWindow::getCurrentDB()
{
    return db;
}

void LoginWindow::on_Login_clicked()
{

    QString DatabaseName = "library";
    QString host = "10.214.6.33";
    QString username = "user573";
    QString password = "086421";

    db.setHostName(host);
    db.setDatabaseName(DatabaseName);
    db.setUserName(username);
    db.setPassword(password);

    if(db.open()){
        QTimer::singleShot(10, this, &LoginWindow::close);
        emit success();
    }
    else{
        QMessageBox::warning(this, "Error", "Failed to connect database.");
    }
}

void LoginWindow::on_Cancel_clicked()
{
    this->close();
    QCoreApplication::quit();
}
