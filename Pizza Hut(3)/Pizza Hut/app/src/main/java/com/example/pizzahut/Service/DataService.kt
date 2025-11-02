package com.example.login.entity

import com.example.pizzahut.R
import com.example.pizzahut.info.RightListInfo

class DataService {
    companion object {
        fun getListData(position: Int): ArrayList<RightListInfo> {
            val list = ArrayList<RightListInfo>()
            if(position==0){
                list.add(RightListInfo(R.mipmap.img_2,"招牌鸡腿堡3件套",29.90,0))
                list.add(RightListInfo(R.mipmap.img_one1,"面饭一人食2件套",34.90,0))
                list.add(RightListInfo(R.mipmap.img_3,"小装披萨4片一人食",34.90,0))
                list.add(RightListInfo(R.mipmap.img_4,"面饭一人食3件套",39.90,0))
            }else if (position==1){
                list.add(RightListInfo(R.mipmap.img_21,"必胜假日双人餐",159.00,0))
                list.add(RightListInfo(R.mipmap.img_22,"必胜假日全家餐",279.00,0))
                list.add(RightListInfo(R.mipmap.img_23,"赛利亚勇士欢庆套餐",169.00,0))
            }else if (position==2){
                list.add(RightListInfo(R.mipmap.img_31,"不玩虚的过瘾三人餐",189.00,0))
                list.add(RightListInfo(R.mipmap.img_32,"不玩虚的双旦多人餐",219.00,0))
                list.add(RightListInfo(R.mipmap.img_33,"悠享单人餐",19.00,0))
                list.add(RightListInfo(R.mipmap.img_34,"精选单人餐",25.00,0))
                list.add(RightListInfo(R.mipmap.img_35,"畅享单人餐",39.00,0))
                list.add(RightListInfo(R.mipmap.img_36,"必胜小时餐",59.00,0))
                list.add(RightListInfo(R.mipmap.img_37,"拿了就跑小食盒套餐",39.90,0))
            }else if (position==3){
                list.add(RightListInfo(R.mipmap.img_41,"超级至尊披萨",19.90,0))
                list.add(RightListInfo(R.mipmap.img_42,"经典意式肉酱面",59.00,0))
                list.add(RightListInfo(R.mipmap.img_43,"滋滋鸡腿排配烤肠",19.90,0))
                list.add(RightListInfo(R.mipmap.img_44,"招牌秘制鸡腿堡",19.90,0))
                list.add(RightListInfo(R.mipmap.img_45,"滋滋炭烤风味牛排",59.00,0))
                list.add(RightListInfo(R.mipmap.img_46,"那不勒斯式肉酱香肠焗饭",19.90,0))
                list.add(RightListInfo(R.mipmap.img_47,"纽约客式脆薯格",9.90,0))
                list.add(RightListInfo(R.mipmap.img_48,"乌梅山楂果味暴打柠檬",19.90,0))
                list.add(RightListInfo(R.mipmap.img_49,"百香果暴打柠檬",59.00,0))
                list.add(RightListInfo(R.mipmap.img_410,"金枕榴莲菠菠披萨",19.90,0))
                list.add(RightListInfo(R.mipmap.img_411,"臻选西冷牛排",9.90,0))
                list.add(RightListInfo(R.mipmap.img_412,"新奥尔良式鸡肉意面",9.90,0))
            }else if (position==4){
                list.add(RightListInfo(R.mipmap.img_51,"招牌鸡腿堡3件套",29.90,0))
                list.add(RightListInfo(R.mipmap.img_52,"招牌鸡腿堡4件套",41.00,0))
                list.add(RightListInfo(R.mipmap.img_53,"招牌牛肉堡3件套",42.90,0))
                list.add(RightListInfo(R.mipmap.img_54,"招牌牛肉堡4件套",51.00,0))
                list.add(RightListInfo(R.mipmap.img_55,"招牌秘制鸡腿堡",19.90,0))
                list.add(RightListInfo(R.mipmap.img_56,"招牌香辣鸡腿堡",19.90,0))
                list.add(RightListInfo(R.mipmap.img_57,"0添加安格斯牛肉堡",32.00,0))
                list.add(RightListInfo(R.mipmap.img_58,"芝士和牛至尊堡",32.00,0))
            }else if(position==5){
                list.add(RightListInfo(R.mipmap.img_61,"双拼披萨",57.95,0))
                list.add(RightListInfo(R.mipmap.img_62,"奶香芝士多多披萨",39.00,0))
                list.add(RightListInfo(R.mipmap.img_63,"意式肉酱风味嫩鸡披萨",39.00,0))
                list.add(RightListInfo(R.mipmap.img_64,"薯角嫩鸡披萨",49.00,0))
                list.add(RightListInfo(R.mipmap.img_65,"鎏金咸蛋黄嫩鸡披萨",59.00,0))
                list.add(RightListInfo(R.mipmap.img_66,"夏威夷风情嫩鸡披萨",59.00,0))
                list.add(RightListInfo(R.mipmap.img_67,"鎏金咸蛋黄南美白虾披萨",69.00,0))
                list.add(RightListInfo(R.mipmap.img_68,"夏威夷风情南美白虾披萨",69.00,0))
                list.add(RightListInfo(R.mipmap.img_69,"新奥尔良风情烤肉披萨",69.00,0))
                list.add(RightListInfo(R.mipmap.img_610,"南美白虾海鲜至尊披萨",79.00,0))
                list.add(RightListInfo(R.mipmap.img_612,"意式肉酱披萨",36.90,0))
                list.add(RightListInfo(R.mipmap.img_613,"薯角培根披萨",49.00,0))
                list.add(RightListInfo(R.mipmap.img_614,"美式腊肠多多披萨",49.00,0))
                list.add(RightListInfo(R.mipmap.img_615,"意式肉酱风味烤肠披萨",49.00,0))
                list.add(RightListInfo(R.mipmap.img_616,"鎏金咸蛋黄培根披萨",49.00,0))
                list.add(RightListInfo(R.mipmap.img_617,"路易安那风味牛肉酱披萨",49.00,0))
                list.add(RightListInfo(R.mipmap.img_618,"豪华大满贯披萨",59.00,0))
            }else if (position==6){
                list.add(RightListInfo(R.mipmap.img_71,"新奥尔良式鸡肉意面",19.90,0))
                list.add(RightListInfo(R.mipmap.img_72,"经典意式肉酱面",28.00,0))
                list.add(RightListInfo(R.mipmap.img_73,"太平洋明太子奶油蛤蜊面",35.00,0))
                list.add(RightListInfo(R.mipmap.img_74,"地中海式奶油培根意面",35.00,0))
                list.add(RightListInfo(R.mipmap.img_75,"麻辣小龙虾意面",35.00,0))
                list.add(RightListInfo(R.mipmap.img_76,"那不勒斯式肉酱香肠焗饭",19.90,0))
                list.add(RightListInfo(R.mipmap.img_77,"照烧鸡肉炒饭",28.00,0))
                list.add(RightListInfo(R.mipmap.img_78,"安格斯牛肉酱焗饭",33.00,0))
                list.add(RightListInfo(R.mipmap.img_79,"巴塞罗那式海鲜饭",33.00,0))
            }else if (position==7){
                list.add(RightListInfo(R.mipmap.img_81,"必胜小食盒",39.90,0))
                list.add(RightListInfo(R.mipmap.img_82,"芝士挞",9.00,0))
                list.add(RightListInfo(R.mipmap.img_83,"地中海式烤蛤蜊",15.00,0))
                list.add(RightListInfo(R.mipmap.img_84,"意式干白芝士焗贻贝",19.00,0))
                list.add(RightListInfo(R.mipmap.img_85,"香草凤尾虾",19.00,0))
                list.add(RightListInfo(R.mipmap.img_86,"厚切薯条",12.00,0))
                list.add(RightListInfo(R.mipmap.img_87,"纽约客式脆薯格",9.90,0))
                list.add(RightListInfo(R.mipmap.img_88,"芝士焗黄金甜玉米粒",9.90,0))
                list.add(RightListInfo(R.mipmap.img_89,"嘎嘣黄金鸡脆骨",12.00,0))
                list.add(RightListInfo(R.mipmap.img_810,"椒麻小酥肉",12.00,0))
                list.add(RightListInfo(R.mipmap.img_811,"浓情烤翅",15.00,0))
                list.add(RightListInfo(R.mipmap.img_812,"炙烤香骨鸡",19.00,0))
                list.add(RightListInfo(R.mipmap.img_813,"川香傲椒脆翅尖8只",12.00,0))
                list.add(RightListInfo(R.mipmap.img_814,"浓情香鸡翼",19.00,0))
                list.add(RightListInfo(R.mipmap.img_815,"韩式炸鸡",19.00,0))
            } else if (position==8){
                list.add(RightListInfo(R.mipmap.img_91,"美式炙烤黑椒牛排",59.00,0))
                list.add(RightListInfo(R.mipmap.img_92,"臻选西冷牛排",39.90,0))
                list.add(RightListInfo(R.mipmap.img_93,"滋滋鸡腿排配烤肠",29.00,0))
                list.add(RightListInfo(R.mipmap.img_94,"滋滋炭烧风味牛排",59.00,0))
                list.add(RightListInfo(R.mipmap.img_95,"普罗旺斯风味牛排",59.00,0))
                list.add(RightListInfo(R.mipmap.img_96,"招牌肋眼牛排",75.00,0))
            }else if(position==9){
                list.add(RightListInfo(R.mipmap.img_101,"鸡茸蘑菇汤",19.00,0))
                list.add(RightListInfo(R.mipmap.img_102,"提拉米苏•云朵",19.00,0))
                list.add(RightListInfo(R.mipmap.img_103,"芝香吐司烤布蕾",9.90,0))
                list.add(RightListInfo(R.mipmap.img_104,"冰淇淋（单球/双球）",9.90,0))
                list.add(RightListInfo(R.mipmap.img_105,"经典芝士烤榴莲",19.00,0))
                list.add(RightListInfo(R.mipmap.img_106,"萌团团动物园芝士蛋糕",19.00,0))
            }
            else{
                list.add(RightListInfo(R.mipmap.img_111,"茉莉芝士轻乳茶",15.00,0))
                list.add(RightListInfo(R.mipmap.img_112,"大红袍芝士轻乳茶",15.00,0))
                list.add(RightListInfo(R.mipmap.img_113,"乌梅山楂果味暴打柠檬",12.00,0))
                list.add(RightListInfo(R.mipmap.img_114,"武夷岩茶大红袍梅梅茶",12.00,0))
                list.add(RightListInfo(R.mipmap.img_115,"武夷岩茶大红袍柠檬茶",15.00,0))
                list.add(RightListInfo(R.mipmap.img_116,"三重柠檬特调",9.90,0))
                list.add(RightListInfo(R.mipmap.img_117,"醇香七窨茉莉茶饮",9.90,0))
                list.add(RightListInfo(R.mipmap.img_118,"武夷大红袍茶饮",9.90,0))
                list.add(RightListInfo(R.mipmap.img_119,"百香果暴打柠檬",15.00,0))
                list.add(RightListInfo(R.mipmap.img_1110,"百香果大红袍轻柠茶",15.00,0))
                list.add(RightListInfo(R.mipmap.img_1111,"百香果七窨茉莉茶",15.00,0))
                list.add(RightListInfo(R.mipmap.img_1112,"百事可乐",8.00,0))
                list.add(RightListInfo(R.mipmap.img_1113,"阳光橙橙（橙汁饮料）",19.00,0))
            }
            return list
        }
    }
}