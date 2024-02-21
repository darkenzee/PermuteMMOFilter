package io.github.darkenzee.PermuteMMOFilter;

import io.github.darkenzee.PermuteMMOFilter.parser.PathDetails;
import io.github.darkenzee.PermuteMMOFilter.tables.GenericTableModel;
import io.github.darkenzee.PermuteMMOFilter.tables.RowConverter;
import io.github.darkenzee.PermuteMMOFilter.types.AnyYesNo;
import io.github.darkenzee.PermuteMMOFilter.types.PokemonGender;
import io.github.darkenzee.PermuteMMOFilter.types.ShinyType;

public class PathDetailsTableModel extends GenericTableModel<PathDetails> {

	private static final long serialVersionUID = 2875856519447849095L;

	private static String[] COLUMN_NAMES = new String[] { "Path", ShinyType.Any.symbol(), "Species",
			PokemonGender.Any.symbol(), "α", "Nature", "CR", "MR", "BR", "SK", "MS", "AG", "SA" };

	private static Class<?>[] COLUMN_TYPES = new Class<?>[] { String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class, String.class, String.class, String.class,
			String.class, String.class };

	private static RowConverter<PathDetails> ROW_CONVERTER = new RowConverter<PathDetails>() {

		@Override
		public Object[] convertRow(PathDetails path) {
			return new Object[] { path.getPath(), path.getShinyType().symbol(), path.getSpecies(),
					path.getGender().symbol(), path.isAlpha() ? "α" : "", path.getNature(),
					booleanToYesNo(path.isChain()), booleanToYesNo(path.isSpawnsMultipleResults()),
					booleanToYesNo(path.isBonus()), booleanToYesNo(path.isSkittish()),
					booleanToYesNo(path.isSkittishMultiScaring()), booleanToYesNo(path.isSkittishAggressive()),
					booleanToYesNo(path.isSingleAdvances()) };
		}
	};

	private static String booleanToYesNo(boolean value) {
		return value ? AnyYesNo.Yes.toString() : AnyYesNo.No.toString();
	}

	public PathDetailsTableModel() {
		super(COLUMN_NAMES, COLUMN_TYPES, ROW_CONVERTER);
	}
}
