#include "mainwindow.h"
#include "loginwindow.h"
#include <QApplication>
int main(int argc, char *argv[])
{
    QTextCodec *codec=QTextCodec::codecForName("utf-8");
    QTextCodec::setCodecForLocale(codec);
    QApplication a(argc, argv);
    LoginWindow login;
    MainWindow mainWindow;
    QObject::connect(&login, &LoginWindow::success, &mainWindow, &MainWindow::show);
    login.show();
    mainWindow.getDatabase(login.getCurrentDB());

    return a.exec();
}
