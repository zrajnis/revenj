module test {
	value Simple {
		int number;
		text text;
		En? en;
		En en2;
		bool? nb;
		timestamp ts;
	}
	enum En { A; B; }
	root LazyLoad {
		Composite? *comp;
		SingleDetail? *sd;
	}
	root SingleDetail {
		detail<LazyLoad.sd> details;
	}
	root Composite(id) {
		uuid id;
		En[] enn;
		En en;
		Simple simple;
		List<timestamp> tsl;
		List<Entity> entities;
		detail<LazyLoad.comp> lazies;
		specification ForSimple 'it => it.simple.number == simple.number' {
			Simple simple;
		}
	}
	snowflake<Composite> CompositeList {
		id;
		enn;
		en;
		tsl;
		entities;
		simple;
		specification ForSimple 'it => simples.Contains(it.simple)' {
			List<Simple> simples;
		}
	}
	entity Entity {
		money money;
		string(10) id;
		Composite? *composite;
	}
	event Clicked {
		date? date;
		decimal number;
		long? bigint;
		Set<bool> bool;
		En? en;

		specification BetweenNumbers 'it => it.number >= min && inSet.Contains(it.number) && it.en == en' {
			decimal min;
			Set<decimal> inSet;
			En? en;
		}
	}
	report FindMany {
		uuid id;
		Set<uuid> ids;
		List<CompositeList> composites 'it => ids.Contains(it.id)' order by id limit 4;
		Composite found 'it => it.id == id';
	}
}
module Seq {
	root Next {
		specification BetweenIds 'it => min == null || it.ID >= min.Value && it.ID <= max' {
			int? min;
			int max;
		}
	}
}
module mixinReference {
	mixin Report {
		Author* author;
	}
	aggregate SpecificReport {
		has mixin Report;
	}
	aggregate Author {
		string name;
	}
}
