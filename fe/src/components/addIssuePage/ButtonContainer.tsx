type Props = {
  children?: React.ReactNode;
};

export const ButtonContainer: React.FC<Props> = ({ children }) => {
  return <div css={bodyContainerStyle}>{children}</div>;
};

const bodyContainerStyle = {
  display: 'flex',
  justifyContent: 'flex-end',
  alignItems: 'center',
};
