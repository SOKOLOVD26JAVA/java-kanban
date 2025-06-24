package managers.AllManagersTest;

import main.ru.yandex.practicum.manager.InMemoryTaskManager;

public class InMemoryTest extends AbstractManagersTest<InMemoryTaskManager> {
    @Override
    protected InMemoryTaskManager getManager() {
        return new InMemoryTaskManager();
    }

}
