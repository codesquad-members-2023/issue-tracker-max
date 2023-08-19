type Props = {
  isLabel?: boolean;
  title: string;
  items: {
    id: number;
    name: string;
    textColor?: string;
    backgroundColor?: string;
  }[];
};

export default function DropdownFilter({
  isLabel = false,
  title,
  items,
}: Props) {
  return (
    <div className="dropdown-filter">
      <div className="dropdown-filter-info">{title}</div>
      <ul className="filter-list">
        {isLabel ? (
          <>
            {items.map((item) => (
              <li key={item.id}>
                <div className="label-color" />
                {item.name}
              </li>
            ))}
          </>
        ) : (
          <>
            {items.map((item) => {
              return <li key={item.id}>{item.name}</li>;
            })}
          </>
        )}
      </ul>
    </div>
  );
}
