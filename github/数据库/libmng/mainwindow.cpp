#include "mainwindow.h"
#include "ui_mainwindow.h"

bool IfLogin_as_admin = false;
QString op;

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    ui->AddBooks->setVisible(false);
    ui->ExitLogin->setVisible(false);
    ui->Modify_Admins->setVisible(false);
    ui->LandCard->setVisible(false);
    ui->LendBooks->setVisible(false);
    ui->ReturnBooks->setVisible(false);

    QVBoxLayout *layout = new QVBoxLayout();
    layout->addWidget(ui->AddBooks);
    layout->addWidget(ui->ExitLogin);
    this->setLayout(layout);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_SearchBooks_clicked()
{
    ui->stackedWidget->setCurrentIndex(0);
}

void MainWindow::on_AddBooks_clicked()
{
    ui->stackedWidget->setCurrentIndex(1);
}

void MainWindow::getDatabase(QSqlDatabase db)
{
    book_db = db;
}

void MainWindow::on_TokenImportBooks_clicked()
{
    QString filePath = QFileDialog::getOpenFileName(this, tr("选择数据源"), ".", tr("文本文件(*.txt)"));
    if (filePath.isEmpty()) {
            return;
        }
    QFile file(filePath);
    file.open(QIODevice::ReadOnly);
    qDebug()<<filePath;
    QTextStream fin(&file);
    while (true) {
        QString book_category;
        QString book_name;
        QString publisher;
        int publication_year;
        QString author;
        double price;
        int total_amount;
        int stock_amount;
        QString end;
        fin>>end;
        if(end=="#") break;
        book_category=end;

        fin>>book_name>>publisher>>publication_year>>author>>price>>total_amount>>stock_amount;
        qDebug()<<publication_year;
        QSqlQuery query;
        QDateTime dateTime(QDateTime::currentDateTime());
        QString time = dateTime.toString("yyyy-MM-dd hh:mm:ss");
        query.prepare("INSERT INTO Books (BookType, BookName, Publisher, Year, Author, Pirce, Total, Storage ,UpdateTime)"
                      "VALUES (:book_category, :book_name, :publisher, :publication_year, :author, :price, :total_amount, :stock_amount,:time)");
        query.bindValue(":book_category", book_category);
        query.bindValue(":book_name", book_name);
        query.bindValue(":publisher", publisher);
        query.bindValue(":publication_year", publication_year);
        query.bindValue(":author", author);
        query.bindValue(":price", price);
        query.bindValue(":total_amount", total_amount);
        query.bindValue(":stock_amount", stock_amount);
        query.bindValue(":time", time);
        if (!query.exec()) {
            qDebug() << "Insert into book_info failed:" << query.lastError().text();
        }
    }
}

void MainWindow::on_OktoAddBooks_clicked()
{
    QSqlQuery query;
    if(book_db.open()) {
        qDebug()<<"yes"<<book_db.lastError().text();
    }
    for (int row = 0; row < ui->tableWidget->rowCount(); row++) {
        QString category = ui->tableWidget->item(row, 0) ? ui->tableWidget->item(row, 0)->text() : "";
        QString book_name = ui->tableWidget->item(row, 1) ? ui->tableWidget->item(row, 1)->text() : "";
        QString publisher = ui->tableWidget->item(row, 2) ? ui->tableWidget->item(row, 2)->text() : "";
        QString pub_year = ui->tableWidget->item(row, 3) ? ui->tableWidget->item(row, 3)->text() : "";
        int publication_year = pub_year.toInt();
        QString author = ui->tableWidget->item(row, 4) ? ui->tableWidget->item(row, 4)->text() : "";
        QString value = ui->tableWidget->item(row, 5) ? ui->tableWidget->item(row, 5)->text() : "";
        double price = value.toDouble();
        QString total_num = ui->tableWidget->item(row, 6) ? ui->tableWidget->item(row, 6)->text() : "";
        QString stock_num = ui->tableWidget->item(row, 7) ? ui->tableWidget->item(row, 7)->text() : "";
        int total_amount = total_num.toInt();
        int stock_amount = stock_num.toInt();
        QDateTime dateTime(QDateTime::currentDateTime());
        QString time = dateTime.toString("yyyy-MM-dd hh:mm:ss");
        QString sql = QString("INSERT INTO Books (BookType, BookName, Publisher, Year, Author, Pirce, Total, Storage ,UpdateTime) "
                              "VALUES ( '%1', '%2', '%3', '%4', '%5', %6, '%7', '%8','%9')").arg(category).arg(book_name)
                .arg(publisher).arg(publication_year).arg(author).arg(price).arg(total_amount).arg(stock_amount).arg(time);
        qDebug()<<sql<<endl;
        int SuccessIns = query.exec(sql);
        if(SuccessIns) {
            qDebug()<<"success";
        } else {
            qDebug()<<"failed!";
        }
    }
}

void MainWindow::on_AddRows_clicked()
{
    int row = ui->tableWidget->rowCount();
    ui->tableWidget->insertRow(row);
}

void MainWindow::on_DelRows_clicked()
{
    int row = ui->tableWidget->rowCount() - 1;
    if (row >= 0) {
        ui->tableWidget->removeRow(row);
    }
}

void MainWindow::on_SearchinSelectedMode_clicked()
{
    QString queryText = ui->QueryInfo->text();
    QString search_type = ui->SearchModeSelect->currentText();
    QString search_precise_or_random = ui->selectSearcBookName_mood->currentText();
    QString sql = "";
    QSqlQuery query;
    int search_mode;
    if (search_type == "类别") search_mode = 0;
    if (search_type == "书名") search_mode = 1;
    if (search_type == "出版社") search_mode = 2;
    if (search_type == "年份") search_mode = 3;
    if (search_type == "作者") search_mode = 4;
    if (search_type == "价格") search_mode = 5;
    switch(search_mode) {
        case 0:
            if(search_precise_or_random == "精确") {
                sql = QString("SELECT * FROM Books WHERE BookType = '%1'").arg(queryText);
            } else {
                sql = QString("SELECT * FROM Books WHERE BookType LIKE '%%1%'").arg(queryText);
            }
            break;
        case 1:
            if(search_precise_or_random == "精确") {
                sql = QString("SELECT * FROM Books WHERE BookName = '%1'").arg(queryText);
            } else {
                sql = QString("SELECT * FROM Books WHERE BookName LIKE '%%1%'").arg(queryText);
            }
            break;
        case 2:
            if(search_precise_or_random == "精确") {
                sql = QString("SELECT * FROM Books WHERE Publisher = '%1'").arg(queryText);
            } else {
                sql = QString("SELECT * FROM Books WHERE Publisher LIKE '%%1%'").arg(queryText);
            }
            break;
        case 3:
            if(search_precise_or_random == "精确") {
                sql = QString("SELECT * FROM Books WHERE Year = '%1'").arg(queryText);
            } else {
                sql = QString("SELECT * FROM Books WHERE Year LIKE '%%1%'").arg(queryText);
            }
            break;
        case 4:
            if(search_precise_or_random == "精确") {
                sql = QString("SELECT * FROM Books WHERE Author = '%1'").arg(queryText);
            } else {
                sql = QString("SELECT * FROM Books WHERE Author LIKE '%%1%'").arg(queryText);
            }
            break;
        case 5:
            if(search_precise_or_random == "精确") {
                sql = QString("SELECT * FROM Books WHERE Pirce = %1").arg(queryText);
            } else {
                sql = QString("SELECT * FROM Books WHERE Pirce = %1").arg(queryText);
            }
        break;
    }
    query.exec(sql);
    ui->ResultTableWidget->setRowCount(0);

    int row = 0;
    while(query.next()){
        ui->ResultTableWidget->insertRow(row);
        QTableWidgetItem *book_id = new QTableWidgetItem(query.value(0).toString());
        QTableWidgetItem *book_category = new QTableWidgetItem(query.value(1).toString());
        QTableWidgetItem *book_name = new QTableWidgetItem(query.value(2).toString());
        QTableWidgetItem *publisher = new QTableWidgetItem(query.value(3).toString());
        QTableWidgetItem *publication_year = new QTableWidgetItem(query.value(4).toString());
        QTableWidgetItem *author = new QTableWidgetItem(query.value(5).toString());
        QTableWidgetItem *price = new QTableWidgetItem(query.value(6).toString());
        QTableWidgetItem *total_amount = new QTableWidgetItem(query.value(7).toString());
        QTableWidgetItem *stock_amount = new QTableWidgetItem(query.value(8).toString());
        QTableWidgetItem *uptime = new QTableWidgetItem(query.value(9).toString());
        ui->ResultTableWidget->setItem(row, 0, book_id);
        ui->ResultTableWidget->setItem(row, 1, book_category);
        ui->ResultTableWidget->setItem(row, 2, book_name);
        ui->ResultTableWidget->setItem(row, 3, publisher);
        ui->ResultTableWidget->setItem(row, 4, publication_year);
        ui->ResultTableWidget->setItem(row, 5, author);
        ui->ResultTableWidget->setItem(row, 6, price);
        ui->ResultTableWidget->setItem(row, 7, total_amount);
        ui->ResultTableWidget->setItem(row, 8, stock_amount);
        ui->ResultTableWidget->setItem(row, 9, uptime);
        row++;
    }
    if(row == 0){
        ui->ResultTableWidget->insertRow(0);
        QTableWidgetItem *emptyResult = new QTableWidgetItem("无匹配图书");
        ui->ResultTableWidget->setItem(0, 0, emptyResult);
    }
}

void MainWindow::on_LoginButton_clicked()
{
    ui->stackedWidget->setCurrentIndex(6);
}

void MainWindow::on_Login_in_mode_clicked()
{
    QString LoginName = ui->LoginName->text();
    QString LoginPassword = ui->LoginPassword->text();
    QSqlQuery query;
    query.prepare("SELECT * FROM Users WHERE UserID = :username AND Password = :password");
    query.bindValue(":username", LoginName);
    query.bindValue(":password", LoginPassword);
    if(query.exec() && query.next()) {
        ui->Login_info_Warning->clear();
        ui->Login_info_Warning->append("登陆成功！");
        IfLogin_as_admin = true;
        ui->AddBooks->setVisible(true);
        ui->ExitLogin->setVisible(true);
        ui->Modify_Admins->setVisible(true);
        ui->LandCard->setVisible(true);
        ui->LendBooks->setVisible(true);
        ui->ReturnBooks->setVisible(true);
        op=query.value("Name").toString();
    } else {
        ui->Login_info_Warning->clear();
        ui->Login_info_Warning->append("用户名或密码错误！");
    }
}

void MainWindow::on_ExitLogin_clicked()
{
    QMessageBox::warning(this, tr("警告"), tr("登录已经退出！"));
    IfLogin_as_admin = false;
    ui->AddBooks->setVisible(false);
    ui->ExitLogin->setVisible(false);
    ui->Modify_Admins->setVisible(false);
    ui->LandCard->setVisible(false);
    ui->LendBooks->setVisible(false);
    ui->ReturnBooks->setVisible(false);
}

void MainWindow::on_Admin_add_account_clicked()
{
    QSqlQuery query;
    QString username = ui->get_admin_accname->text();
    QString password = ui->get_admin_password->text();
    QString name = ui->get_admin_name->text();
    QString contact = ui->get_admin_contact->text();
    query.prepare("INSERT INTO Users (UserID, Password, Name, Contact) "
                  "VALUES (:username, :password, :name, :contact)");
    query.bindValue(":username", username);
    query.bindValue(":password", password);
    query.bindValue(":name", name);
    query.bindValue(":contact", contact);

    if (!query.exec()) {
        qDebug() << "Failed to insert data:" << query.lastError().text();
        return;
    }
    qDebug() << "Data inserted successfully";
}

void MainWindow::on_Admin_del_account_clicked()
{
    QSqlQuery query;
    QString username = ui->to_del_admin_accname->text();
    QString password = ui->to_del_admin_password->text();
    query.prepare("SELECT * FROM Users WHERE UserID = :username AND Password = :password");
    query.bindValue(":username", username);
    query.bindValue(":password", password);
    if(query.exec() && query.next()) {
        query.prepare("DELETE FROM Users WHERE UserID = :username AND Password = :password");
        query.bindValue(":username", username);
        query.bindValue(":password", password);
        if (query.exec()) {
            ui->Acc_manage_Warning->clear();
            ui->Acc_manage_Warning->append("删除成功！");
        } else {
            ui->Acc_manage_Warning->clear();
            ui->Acc_manage_Warning->append("删除失败！");
        }
    } else {
        ui->Acc_manage_Warning->clear();
        ui->Acc_manage_Warning->append("用户名或密码错误！");
    }
}

void MainWindow::on_Modify_Admins_clicked()
{
    ui->stackedWidget->setCurrentIndex(7);
}

void MainWindow::on_LendBooks_clicked()
{
    ui->stackedWidget->setCurrentIndex(3);
}

void MainWindow::on_BooklendCard_id_confirm_clicked()
{
    QString card_no = ui->BooklandCard_id->text();
    QString sql = "";
    QSqlQuery query;
    sql = QString("SELECT * FROM LibraryRecords WHERE CardNo = '%1'").arg(card_no);
    query.exec(sql);
    ui->Books_lent_by_id->clearContents();
    ui->Books_lent_by_id->setRowCount(0);

    int row = 0;
    while(query.next()){

        ui->Books_lent_by_id->insertRow(row);

        QTableWidgetItem *checkBoxItem = new QTableWidgetItem();
        checkBoxItem->setFlags(Qt::ItemIsUserCheckable | Qt::ItemIsEnabled);
        checkBoxItem->setCheckState(Qt::Unchecked);
        ui->Books_lent_by_id->setItem(row, 0, checkBoxItem);

        QTableWidgetItem *book_no = new QTableWidgetItem(query.value(2).toString());
        QTableWidgetItem *borrow_date = new QTableWidgetItem(query.value(3).toString());
        QTableWidgetItem *handler_name = new QTableWidgetItem(query.value(5).toString());

        ui->Books_lent_by_id->setItem(row, 1, book_no);
        ui->Books_lent_by_id->setItem(row, 2, borrow_date);
        ui->Books_lent_by_id->setItem(row, 3, handler_name);
        row++;
    }
    if(row == 0){
        ui->Books_lent_by_id->insertRow(0);
        QTableWidgetItem *emptyResult = new QTableWidgetItem("无借书记录！");
        ui->Books_lent_by_id->setItem(0, 0, emptyResult);
    }

}

void MainWindow::on_Book_id_to_lend_confirm_clicked()
{
    QString book_id = ui->BookId_to_lend->text();
    int id=book_id.toInt();
    QString sql = "";
    QSqlQuery query;
    sql = QString("SELECT * FROM Books WHERE BookNo = %1").arg(id);

    if(query.exec(sql)) {
        qDebug()<<"Success in select";
    }
    ui->Book_in_id_tolend->setRowCount(0);

    int row = 0;
    while(query.next()){
        ui->Book_in_id_tolend->insertRow(row);

        QTableWidgetItem *checkBoxItem = new QTableWidgetItem();
        checkBoxItem->setFlags(Qt::ItemIsUserCheckable | Qt::ItemIsEnabled);
        checkBoxItem->setCheckState(Qt::Unchecked);
        ui->Book_in_id_tolend->setItem(row, 0, checkBoxItem);

        QTableWidgetItem *book_id = new QTableWidgetItem(query.value(0).toString());
        QTableWidgetItem *book_category = new QTableWidgetItem(query.value(1).toString());
        QTableWidgetItem *book_name = new QTableWidgetItem(query.value(2).toString());
        QTableWidgetItem *publisher = new QTableWidgetItem(query.value(3).toString());
        QTableWidgetItem *publication_year = new QTableWidgetItem(query.value(4).toString());
        QTableWidgetItem *author = new QTableWidgetItem(query.value(5).toString());
        QTableWidgetItem *price = new QTableWidgetItem(query.value(6).toString());
        QTableWidgetItem *stock_amount = new QTableWidgetItem(query.value(8).toString());
        QTableWidgetItem *uptime = new QTableWidgetItem(query.value(9).toString());
        ui->Book_in_id_tolend->setItem(row, 1, book_id);
        ui->Book_in_id_tolend->setItem(row, 2, book_category);
        ui->Book_in_id_tolend->setItem(row, 3, book_name);
        ui->Book_in_id_tolend->setItem(row, 4, publisher);
        ui->Book_in_id_tolend->setItem(row, 5, publication_year);
        ui->Book_in_id_tolend->setItem(row, 6, author);
        ui->Book_in_id_tolend->setItem(row, 7, price);
        ui->Book_in_id_tolend->setItem(row, 8, stock_amount);
        ui->Book_in_id_tolend->setItem(row, 9, uptime);
        row++;
    }

    if(row == 0){
        ui->Book_in_id_tolend->insertRow(0);
        QTableWidgetItem *emptyResult = new QTableWidgetItem("无匹配图书");
        ui->Book_in_id_tolend->setItem(0, 0, emptyResult);
    }
}

void MainWindow::on_Confirm_to_lendbook_clicked()
{
    QString Lend_Card_no = ui->lendCard_id_forlend->text();
    int no=Lend_Card_no.toInt();
    QString sql1 = QString("SELECT * FROM LibraryCard WHERE CardNo = %1").arg(no);
    QSqlQuery query1;
    query1.exec(sql1);
    if(query1.next()){
        for (int i = 0; i < ui->Book_in_id_tolend->rowCount(); i++) {
            QTableWidgetItem *item = ui->Book_in_id_tolend->item(i, 0);
            if (item && item->checkState() == Qt::Checked) {
                //qDebug()<<"book_id:";
                int row = item->row();
                QTableWidgetItem *item1 = ui->Book_in_id_tolend->item(row, 1); // 获取该行第二列的item
                int book_id = item1->text().toInt(); // 获取该行第二列的文本内容
                // qDebug()<<"book_id:"<<book_id;
                QString sql2 = QString("SELECT * FROM Books WHERE BookNo = %1").arg(book_id);  // 构造 SQL 语句
                QSqlQuery query2;
                query2.exec(sql2);  // 执行查询操作
                if (query2.next()) {  // 如果查询到了记录
                    int stock_amount = query2.value("Storage").toInt();  // 获取库存数量
                        //qDebug()<<"num:"<<stock_amount;
                    if (stock_amount == 0) {
                        QMessageBox::warning(this, tr("提示"), tr("没有库存"), QMessageBox::Ok);
                    } else {
                        sql2 = QString("UPDATE Books SET Storage = %1 WHERE BookNo = %2").arg(stock_amount - 1).arg(book_id);
                        query2.exec(sql2);
                        QSqlQuery query;
                        query.prepare("INSERT INTO LibraryRecords (CardNo, BookNo,LentDate , Operator) "
                                      "VALUES (:card_no, :book_no, :borrow_date, :handler_name)");
                        query.bindValue(":card_no", Lend_Card_no);
                        query.bindValue(":book_no", book_id);
                        query.bindValue(":handler_name", op);
                        QDateTime dateTime(QDateTime::currentDateTime());
                        QString time = dateTime.toString("yyyy-MM-dd hh:mm:ss");
                        query.bindValue(":borrow_date", time);
                        query.exec();
                        QMessageBox::information(this, tr("提示"), tr("借阅成功"), QMessageBox::Ok);
                    }
                } else {
                    QMessageBox::warning(this, tr("错误"), tr("查询失败"), QMessageBox::Ok);
                }
            }
        }
    } else {
        QMessageBox::warning(this, "Warning", "卡号不正确，请重新输入！");
    }
}

void MainWindow::on_AddCards_add_clicked()
{
    int row = ui->AddCards_table->rowCount();
    ui->AddCards_table->insertRow(row);
}

void MainWindow::on_AddCards_del_clicked()
{
    int row = ui->AddCards_table->rowCount() - 1;
    if (row >= 0) {
        ui->AddCards_table->removeRow(row);
    }
}

void MainWindow::on_AddCards_confirm_clicked()
{
    QSqlQuery query;

    for (int row = 0; row < ui->AddCards_table->rowCount(); row++) {
        QString Name = ui->AddCards_table->item(row, 0)->text();
        QString Department = ui->AddCards_table->item(row, 1)->text();
        QString CardType = ui->AddCards_table->item(row, 2)->text();
        QDateTime dateTime(QDateTime::currentDateTime());
        QString time = dateTime.toString("yyyy-MM-dd hh:mm:ss");
        QString sql = QString("INSERT INTO LibraryCard(Name, Department, CardType, UpdateTime) "
                              "VALUES ('%1', '%2', '%3', '%4')").arg(Name).arg(Department).arg(CardType).arg(time);
        int SuccessIns = query.exec(sql);

        if(SuccessIns) {
            qDebug()<<"success";
        } else {
            qDebug()<<"failed!"<<query.lastError().text();
        }
    }
}

void MainWindow::on_LandCard_clicked()
{
    ui->stackedWidget->setCurrentIndex(4);
}

void MainWindow::on_Confirm_to_del_card_clicked()
{
    QString Card_id = ui->Card_id_todel->text();
    QSqlQuery query;
    query.prepare("SELECT * FROM LibraryCard WHERE CardNo = :card_id");
    query.bindValue(":card_id", Card_id);
    if(query.exec() && query.next()) {
        query.prepare("DELETE FROM LibraryCard WHERE CardNo = :card_id");
        query.bindValue(":card_id", Card_id);
        if (query.exec()) {
            ui->Card_manage_Warning->clear();
            ui->Card_manage_Warning->append("删除成功！");
        } else {
            ui->Card_manage_Warning->clear();
            ui->Card_manage_Warning->append("删除失败！");
        }
    } else {
        ui->Card_manage_Warning->clear();
        ui->Card_manage_Warning->append("卡号错误！");
    }
}

void MainWindow::on_ReturnBooks_clicked()
{
    ui->stackedWidget->setCurrentIndex(2);
}

void MainWindow::on_Confirm_to_return_book_clicked()
{
    QString lender = ui->BooklandCard_id->text();
    for (int i = 0; i < ui->Books_lent_by_id->rowCount(); i++) {
        QTableWidgetItem *item = ui->Books_lent_by_id->item(i, 0);
        if (item && item->checkState() == Qt::Checked) {
            int row = item->row();
            QTableWidgetItem *item1 = ui->Books_lent_by_id->item(row, 1); // 获取该行第二列的item
            QString book_no = item1->text(); // 获取该行第二列的文本内容
            QSqlQuery query;
            QString sql = QString("DELETE FROM LibraryRecords where BookNo = '%1' and CardNo ='%2' "
                                  "and Lentdate = (SELECT MIN(Lentdate) FROM LibraryRecords WHERE "
                                  "BookNo = '%3' and CardNo ='%4')").arg(book_no).arg(lender).arg(book_no).arg(lender);
            if(query.exec(sql)) {
                QMessageBox::warning(this, tr("警告"), tr("归还成功！"));
            }
        }
    }
}
