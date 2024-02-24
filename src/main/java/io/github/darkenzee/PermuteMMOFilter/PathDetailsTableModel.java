package io.github.darkenzee.PermuteMMOFilter;

import io.github.darkenzee.PermuteMMOFilter.parser.PathDetails;
import io.github.darkenzee.PermuteMMOFilter.tables.GenericTableModel;
import io.github.darkenzee.PermuteMMOFilter.tables.RowConverter;
import io.github.darkenzee.PermuteMMOFilter.types.PokemonGender;
import io.github.darkenzee.PermuteMMOFilter.types.ShinyType;

public class PathDetailsTableModel extends GenericTableModel<PathDetails> {

	private static final long serialVersionUID = 2875856519447849095L;

	private static String[] COLUMN_NAMES = new String[] { "Path", ShinyType.Any.symbol(), "Species",
			PokemonGender.Any.symbol(), "α", "Nature", "HP", "Atk", "Def", "SAt", "SDf", "Spd", "CR", "MR", "BR", "SK",
			"MS", "AG", "SA" };

	private static Class<?>[] COLUMN_TYPES = new Class<?>[] { String.class, String.class, String.class, String.class,
			String.class, String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
			Integer.class, String.class, String.class, String.class, String.class, String.class, String.class,
			String.class };

	private static RowConverter<PathDetails> ROW_CONVERTER = new RowConverter<PathDetails>() {

		@Override
		public Object[] convertRow(PathDetails path) {
			return new Object[] { path.getPath(), path.getShinyType().symbol(), path.getSpecies(),
					path.getGender().symbol(), path.isAlpha() ? "α" : "", path.getNature(), path.getIVHp(),
					path.getIVAtk(), path.getIVDef(), path.getIVSpAtk(), path.getIVSpDef(), path.getIVSpeed(),
					booleanToTickOrBlank(path.isChain()), booleanToTickOrBlank(path.isSpawnsMultipleResults()),
					booleanToTickOrBlank(path.isBonus()), booleanToTickOrBlank(path.isSkittish()),
					booleanToTickOrBlank(path.isSkittishMultiScaring()),
					booleanToTickOrBlank(path.isSkittishAggressive()), booleanToTickOrBlank(path.isSingleAdvances()) };
		}
	};

	private static String booleanToTickOrBlank(boolean value) {
		return value ? "✓" : "";
	}

	public PathDetailsTableModel() {
		super(COLUMN_NAMES, COLUMN_TYPES, ROW_CONVERTER);
	}
}
