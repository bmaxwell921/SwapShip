package main.swapship.systems;

import main.swapship.components.other.SpawnerComp;
import main.swapship.factories.EntityFactory;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;

public class EnemySpawnSys extends EntityProcessingSystem {

	private ComponentMapper<SpawnerComp> scm;
	
	public EnemySpawnSys() {
		super(Filter.allComponents(SpawnerComp.class));
	}
	
	@Override
	public void initialize() {
		scm = world.getMapper(SpawnerComp.class);
	}
	
	@Override
	public void process(Entity e) {
		SpawnerComp sc = scm.get(e);
		
		sc.timeLeft -= world.getDelta();
		
		if (sc.timeLeft <= 0) {
			EntityFactory.createEnemy(world);
			sc.timeLeft = sc.startTime;
		}
	}
}
