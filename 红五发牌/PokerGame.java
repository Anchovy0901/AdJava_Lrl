package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PokerGame  {
    public List<Card> pokerCards;
    public List<Player> players;

    public PokerGame(){
        pokerCards = new ArrayList<Card>();
        players = new ArrayList<Player>();
    }

    //显示扑克牌List
    public void showPokeCards(){
        System.out.print("为：[ ");
        for (Card c : pokerCards) {
            System.out.print(c.color+c.num+" ");
        }
        System.out.println("]");
    }
    //显示玩家List
    public void showPlayers(){
        System.out.print("玩家列表为：[");
        for(Player player: players){
            System.out.print(player.getName()+" ");
        }
        System.out.println("]");
    }
    //显示玩家的手牌
    public void showPlayerCards(Player player){
        System.out.println(player.getName()+"手牌为:");
        Collections.sort(player.cards);
        Collections.reverse(player.cards);
        System.out.print("[");
        for(Card c: player.cards){
            if(c.getColor().equals("红桃") && c.getNum().equals("5")){
                System.out.print("红桃5"+" ");
            }
        }
        for(Card c: player.cards){
            if(c.getColor().equals("大王")){
                System.out.print("大王"+" ");
            }
        }
        for(Card c: player.cards){
            if(c.getColor().equals("小王")){
                System.out.print("小王"+" ");
            }
        }
        for(Card c: player.cards){
            if(c.getColor().equals("黑桃") && c.getNum().equals("3")){
                System.out.print("黑桃3"+" ");
            }
        }
        for(Card c: player.cards){
            if(c.getColor().equals("草花") && c.getNum().equals("3")){
                System.out.print("草花3"+" ");
            }
        }
        for(Card c: player.cards){
            if(c.getColor().equals("黑桃") && c.getNum().equals("2")){
                System.out.print("黑桃2"+" ");
            }
        }
        for(Card c: player.cards){
            if(c.getColor().equals("红桃") && c.getNum().equals("2")){
                System.out.print("红桃2"+" ");
            }
        }
        for(Card c: player.cards){
            if(c.getColor().equals("草花") && c.getNum().equals("2")){
                System.out.print("草花2"+" ");
            }
        }
        for(Card c: player.cards){
            if(c.getColor().equals("方片") && c.getNum().equals("2")){
                System.out.print("方片2"+" ");
            }
        }
        for(Card c: player.cards){
            if((c.getColor().equals("红桃") && c.getNum().equals("5"))||c.getColor().equals("大王")||c.getColor().equals("小王")||(c.getColor().equals("黑桃") && c.getNum().equals("3"))
                    ||c.getColor().equals("草花") && c.getNum().equals("3")||c.getColor().equals("黑桃") && c.getNum().equals("2")
                    ||c.getColor().equals("红桃") && c.getNum().equals("2")||c.getColor().equals("草花") && c.getNum().equals("2")||c.getColor().equals("方片") && c.getNum().equals("2")){
                continue;
            }
            System.out.print(c.getColor()+c.getNum()+" ");
        }
        System.out.print("]");
        System.out.println();
    }
    //显示底牌
    public void showBottonCards(){
        Collections.sort(pokerCards);
        System.out.print("底牌");
        showPokeCards();
    }
    //初始化玩家列表
    public  void initPlayers(){
        System.out.println("初始化玩家…………");
        Player player1 = new Player(1,"玩家1");
        Player player2 = new Player(2,"玩家2");
        Player player3 = new Player(3,"玩家3");
        Player player4 = new Player(4,"玩家4");
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        System.out.println("初始化玩家结束…………");
    }

    //初始化两副扑克牌
    public void initCard(){
        System.out.println("初始化扑克牌…………");
        String[] colors = {"黑桃","红桃","草花","方片"};
        String[] nums = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
        for(int i = 0; i < 2; i++){
            for (String color : colors) {
                for(String num : nums) {
                    pokerCards.add(new Card(color, num));
                }
            }
            pokerCards.add(new Card("大王","0"));
            pokerCards.add(new Card("小王","0"));
        }
        System.out.println("初始化结束");
        System.out.println("------------创建成功------------");
        this.showPokeCards();
    }
    //对扑克牌进行洗牌
    public void shuffle(){
        System.out.println("进行洗牌");
        Collections.shuffle(pokerCards);
        this.showPokeCards();
    }
    //发牌
    /**
     * 给每位玩家发牌
     * @param num 每位玩家发到的张数
     */
    public void deal(int num){
        System.out.println("开始发牌…………");
        for(int i = 0; i < 4 ; i++){
            Player p = players.get(i);
            for(int j = 0; j < num; j++){
                p.cards.add(pokerCards.get(0));
                pokerCards.remove(0);
            }
        }
        System.out.println("发牌结束");
    }

    public static void main(String[] args){
        PokerGame pokerGame = new PokerGame();
        pokerGame.initCard();
        System.out.println("扑克牌总数为"+pokerGame.pokerCards.size());
        pokerGame.shuffle();
        pokerGame.initPlayers();
        pokerGame.showPlayers();
        //每位玩家发25张牌
        pokerGame.deal(25);
        for(int i = 0; i < 4; i++){
            pokerGame.showPlayerCards(pokerGame.players.get(i));
        }
        pokerGame.showBottonCards();

    }
    public class Player{
        public int id;
        public String name;
        //使用List存储Card，方便排序
        public List<Card> cards;

        public Player(int id, String name){
            this.id = id;
            this.name = name;
            cards = new ArrayList<Card>();
        }

        public int getId() {
            return id;
        }


        public void setId(int id) {
            this.id = id;
        }


        public String getName() {
            return name;
        }


        public void setName(String name) {
            this.name = name;
        }
    }
    public class Card implements Comparable<Card>{
        public String color;
        public String num;

        public Card(String color, String num){
            this.color = color;
            this.num = num;
        }

        public String getColor() {
            return color;
        }


        public void setColor(String color) {
            this.color = color;
        }

        public String getNum() {
            return num;
        }


        public void setNum(String num) {
            this.num = num;
        }

        public Integer ColorToInt(String color){
            if (color.equals("大王"))
                return 6;
            if (color.equals("小王"))
                return 5;
            if (color.equals("黑桃"))
                return 4;
            if (color.equals("红桃"))
                return 3;
            if (color.equals("草花"))
                return 2;
            if (color.equals("方片"))
                return 1;
            return 0;
        }

        public Integer NumToInt(String num){
            if (num.equals("A"))
                return 14;
            if (num.equals("K"))
                return 13;
            if (num.equals("Q"))
                return 12;
            if (num.equals("J"))
                return 11;
            else
                return Integer.valueOf(num.trim());
        }



        @Override public int compareTo(Card o) {
            int result = ColorToInt(this.color) - ColorToInt(o.color);
            if (result != 0)
                return result;
            else {
                result = NumToInt(this.num) - NumToInt(o.num);
                return result;
            }

        }
    }
}
