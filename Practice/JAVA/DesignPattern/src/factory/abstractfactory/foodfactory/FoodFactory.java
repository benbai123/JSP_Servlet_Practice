package factory.abstractfactory.foodfactory;

import java.util.Calendar;

import factory.abstractfactory.food.Food;

public abstract class FoodFactory {
	public abstract Food produceFood (Calendar cal);
}
