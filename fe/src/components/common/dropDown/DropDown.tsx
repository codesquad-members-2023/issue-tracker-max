export const DropDown: React.FC<Props> = ({
  name,
  options,
  alignment,
  titleName,
}) => {
  return (
    <div css={container}>
      <DropDownIndicator
        name={name}
        alignment={alignment}
        titleName={titleName}
      />
      <DropDownList name={name} options={options} alignment={alignment} />
    </div>
  );
};
