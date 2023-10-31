//Brian Fagundes
package Rooms;
import Game.Adventure;
import Items.Item;

public class Outside extends Room {

    public Outside(String name, String description) {
        super(name, description);
        Item cat = new Item("Cat", "The cat meows back at you. to use use type 'feed'");
        items_.add(cat);
        Item tuna = new Item("Tuna", "The tuna can either kill or save the cat");
        items_.add(tuna);
    }
    @Override
    public void playerEntered() {
        System.out.println("As you go outSide; you notice that a meow sound from somewhere outside");
    }
    @Override
    public boolean handleCommand(String[] subcommands) {
        if( subcommands.length <= 1 ) {
            return false;
        }
        String cmd  = subcommands[0];
        String attr = subcommands[1];

        // unlock, use
        if( cmd.equals( "feed" ) && attr.equals( "cat") ) {

            boolean hasFeed = true;
            for( Item item : Adventure.inventory ) {
                if( item.getName().equals( "tuna" ) ) {
                    hasFeed = true;
                    break;
                }
            }
            if( hasFeed ) {
                System.out.println( "You feed the cat and save him from dying");
            }
            else {
                System.out.println( "You don't feed the cat and the now the cat is dead" );
            }
            return true;
        }
        return false;
    }

}

