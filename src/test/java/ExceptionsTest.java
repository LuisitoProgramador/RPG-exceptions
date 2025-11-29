import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExceptionsTest {

    private static class ConcreteCharacterForTesting extends Character {
        public ConcreteCharacterForTesting(String name, int health) {
            super(name, health);
        }

        @Override
        public void receiveDamage(int damage) {
            super.receiveDamage(damage);
        }

        @Override
        public boolean isAlive() {
            return super.isAlive();
        }

        @Override
        public int getCurrentHealth() {
            return super.getCurrentHealth();
        }

        @Override
        public String getName() {
            return super.getName();
        }

        @Override
        public String getStatus() {
            return super.getStatus();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Warrior: enterBerserkMode() lanza BerserkAlreadyActiveException si ya está furioso")
    void testEnterBerserkModeThrowsExceptionWhenAlreadyFurious()  {
        Warrior warrior = new Warrior("Rager", 100, 20);
        warrior.enterBerserkMode();

        BerserkAlreadyActiveException thrown = assertThrows(
                BerserkAlreadyActiveException.class,
                () -> warrior.enterBerserkMode(),
                "Debería lanzar BerserkAlreadyActiveException cuando ya está furioso"
        );
        assertEquals("Rager ya está en modo furioso", thrown.getMessage());
    }

    @Test
    @Order(2)
    @DisplayName("Mage: castSpell() lanza InsufficientManaException si el maná es insuficiente")
    void testCastSpellThrowsInsufficientManaException() {
        Mage mage = new Mage("LowManaMage", 100, 5);
        Mage target = new Mage("Target", 50, 0);

        InsufficientManaException thrown = assertThrows(
                InsufficientManaException.class,
                () -> mage.castSpell(target),
                "Debería lanzar InsufficientManaException cuando el maná es insuficiente"
        );
        assertEquals("LowManaMage no tiene maná suficiente", thrown.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("Mage: heal() lanza InsufficientManaException si el maná es insuficiente para curar")
    void testHealThrowsInsufficientManaException() {
        Mage healer = new Mage("LowManaHealer", 100, 5);
        Mage injuredAlly = new Mage("Injured", 30, 0);
        injuredAlly.receiveDamage(10);

        InsufficientManaException thrown = assertThrows(
                InsufficientManaException.class,
                () -> healer.heal(injuredAlly),
                "Debería lanzar InsufficientManaException cuando el maná es insuficiente para curar"
        );
        assertEquals("LowManaHealer no tiene maná suficiente", thrown.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Warrior: performHeavyAttack() lanza InvalidTargetException para objetivo nulo")
    void testPerformHeavyAttackThrowsInvalidTargetForNull() {
        Warrior warrior = new Warrior("Attacker", 100, 20);

        InvalidTargetException thrown = assertThrows(
                InvalidTargetException.class,
                () -> warrior.performHeavyAttack(null),
                "Debería lanzar InvalidTargetException para un objetivo nulo"
        );
        assertEquals("No se puede realizar una acción a un objetivo nulo", thrown.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Mage: heal() lanza InvalidTargetException para objetivo nulo")
    void testCastSpellThrowsInvalidTargetForNull() {
        Mage healer = new Mage("Healer", 100, 50);

        InvalidTargetException thrown = assertThrows(
                InvalidTargetException.class,
                () -> healer.heal(null),
                "Debería lanzar InvalidTargetException para un objetivo nulo en heal()"
        );
        assertEquals("No se puede realizar una acción a un objetivo nulo", thrown.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("Mage: castSpell() lanza InvalidTargetException para objetivo nulo")
    void testHealThrowsInvalidTargetForNull() {
        Mage caster = new Mage("Caster", 100, 50);

        InvalidTargetException thrown = assertThrows(
                InvalidTargetException.class,
                () -> caster.castSpell(null),
                "Debería lanzar InvalidTargetException para un objetivo nulo en castSpell()"
        );
        assertEquals("No se puede realizar una acción a un objetivo nulo", thrown.getMessage());
    }

    @Test
    @Order(7)
    @DisplayName("Mage: castSpell() lanza CharacterAlreadyDeadException para objetivo muerto")
    void testCastSpellThrowsCharacterAlreadyDeadForDeadTarget() {
        Mage mage = new Mage("Caster", 100, 50);
        ConcreteCharacterForTesting deadTarget = new ConcreteCharacterForTesting("Corpse", 0);

        CharacterAlreadyDeadException thrown = assertThrows(
                CharacterAlreadyDeadException.class,
                () -> mage.castSpell(deadTarget),
                "Debería lanzar CharacterAlreadyDeadException al lanzar un hechizo a un objetivo muerto"
        );
        assertEquals("Corpse ya está muerto", thrown.getMessage());
    }

    @Test
    @Order(8)
    @DisplayName("Mage: heal() lanza CharacterAlreadyDeadException si se intenta curar un objetivo muerto")
    void testHealThrowsCharacterAlreadyDeadForDeadTarget() {
        Mage healer = new Mage("Healer", 100, 50);
        ConcreteCharacterForTesting deadTarget = new ConcreteCharacterForTesting("Ghost", 0);

        CharacterAlreadyDeadException thrown = assertThrows(
                CharacterAlreadyDeadException.class,
                () -> healer.heal(deadTarget),
                "Debería lanzar CharacterAlreadyDeadException al intentar curar un objetivo muerto"
        );
        assertEquals("Ghost ya está muerto", thrown.getMessage());
    }

    @Test
    @Order(9)
    @DisplayName("Warrior: performHeavyAttack() lanza CharacterAlreadyDeadException para objetivo muerto")
    void testPerformHeavyAttackThrowsCharacterAlreadyDeadForDeadTarget() {
        Warrior warrior = new Warrior("Attacker", 100, 20);
        ConcreteCharacterForTesting deadTarget = new ConcreteCharacterForTesting("Corpse", 0);

        CharacterAlreadyDeadException thrown = assertThrows(
                CharacterAlreadyDeadException.class,
                () -> warrior.performHeavyAttack(deadTarget),
                "Debería lanzar CharacterAlreadyDeadException al atacar un objetivo muerto"
        );
        assertEquals("Corpse ya está muerto", thrown.getMessage());
    }

}
