type Props = {
  children?: React.ReactNode;
};

export const InputContainer: React.FC<Props> = ({ children }) => {
  return (
    <div
      css={{
        display: 'flex',
        flexDirection: 'column',
        // gap: '8px',
        width: '100%',
      }}
    >
      {children}
    </div>
  );
};
