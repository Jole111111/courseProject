#ifndef LOGINWINDOW_H
#define LOGINWINDOW_H

#include <QDialog>
#include <QtSql/QSqlDatabase>
#include <QAbstractButton>
#include <QMessageBox>
#include <QtDebug>
#include <QDebug>
#include <QTimer>
#include <QThread>
#include <QLabel>
#include <QVBoxLayout>
#include <QWidget>

namespace Ui {
class LoginWindow;
}

class LoginWindow : public QDialog
{
    Q_OBJECT

public:
    explicit LoginWindow(QWidget *parent = nullptr);
    ~LoginWindow();
    QSqlDatabase getCurrentDB();

signals:
    void success();

private slots:

    void on_Login_clicked();

    void on_Cancel_clicked();


private:
    Ui::LoginWindow *ui;
    QSqlDatabase db;
    QWidget *centralWidget;
};

#endif // LOGINWINDOW_H
