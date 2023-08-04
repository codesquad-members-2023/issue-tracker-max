type Props = {
  children?: React.ReactNode;
};

export const UserImageContainer: React.FC<Props> = ({ children }) => {
  return <div css={{ height: '616px' }}>{children}</div>;
};
