import java.util.Stack;

public class Calculette {
	// Une pile pour stocker les nombres lors des opérations
	Stack<Double> pile;

	// Constructeur
	public Calculette() {
		this.pile = new Stack<>();
	}

	// Méthode pour additionner les deux derniers nombres de la pile
	public void addition() {
		if (pile.size() < 2) {
			throw new IllegalStateException("Pas assez d'éléments dans la pile pour additionner");
		}
		double b = pile.pop();
		double a = pile.pop();
		pile.push(a + b);
	}

	// Méthode pour soustraire le dernier nombre du précédent
	public void soustraction() {
		if (pile.size() < 2) {
			throw new IllegalStateException("Pas assez d'éléments dans la pile pour soustraire");
		}
		double b = pile.pop();
		double a = pile.pop();
		pile.push(a - b);
	}

	// Méthode pour multiplier les deux derniers nombres de la pile
	public void multiplication() {
		if (pile.size() < 2) {
			throw new IllegalStateException("Pas assez d'éléments dans la pile pour multiplier");
		}
		double b = pile.pop();
		double a = pile.pop();
		pile.push(a * b);
	}

	// Méthode pour diviser le dernier nombre par le précédent
	public void division() {
		if (pile.size() < 2) {
			throw new IllegalStateException("Pas assez d'éléments dans la pile pour diviser");
		}
		double b = pile.pop();
		if (b == 0) {
			throw new ArithmeticException("Division par zéro");
		}
		double a = pile.pop();
		pile.push(a / b);
	}

	// Méthode pour calculer une expression en notation polonaise inverse (NPI)
	public double calculerExpression(String expression) {
		String[] tokens = expression.split(" ");
		
		for (String token : tokens) {
			switch (token) {
				case "+":
					addition();
					break;
				case "-":
					soustraction();
					break;
				case "*":
					multiplication();
					break;
				case "/":
					division();
					break;
				default:
					try {
						pile.push(Double.parseDouble(token));
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException("Expression invalide: " + token);
					}
					break;
			}
		}
		
		if (pile.size() != 1) {
			throw new IllegalStateException("Expression incorrecte, la pile finale contient plus d'un élément");
		}
		
		return pile.pop();
	}

	// Méthode principale pour exécuter des calculs de test
	public static void main(String[] args) {
		Calculette calc = new Calculette();
		
		try {
			// Exemple de calcul simple : (1.0 + 3.0)
			double result1 = calc.calculerExpression("1.0 3.0 +");
			System.out.println("Résultat de '1.0 3.0 +': " + result1);  // Devrait afficher 4.0
			
			// Exemple de calcul avec plusieurs opérations : (1.0 + 3) + (2 / 3.2)
			double result2 = calc.calculerExpression("1.0 3 + 2 3.2 / +");
			System.out.println("Résultat de '1.0 3 + 2 3.2 / +': " + result2);  // Devrait afficher environ 4.625
			
			// Exemple de calcul avec multiplication et addition : (5 * 6) + 4
			double result3 = calc.calculerExpression("5 6 * 4 +");
			System.out.println("Résultat de '5 6 * 4 +': " + result3);  // Devrait afficher 34.0
			
			// Exemple de division par zéro pour tester la gestion des erreurs
			double result4 = calc.calculerExpression("10 0 /");
			System.out.println("Résultat de '10 0 /': " + result4);
		} catch (Exception e) {
			System.out.println("Erreur: " + e.getMessage());
		}
	}
}
