import { useTheme } from '@emotion/react';
import { ErrorMessages } from './ErrorMessages';

type Props = {
  onFileChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  fileStatus: DefaultFileStatusType;
  children: React.ReactNode;
};

export const AddButtons: React.FC<Props> = ({
  onFileChange,
  children,
  fileStatus,
}) => {
  const theme = useTheme() as any;
  return (
    <div
      css={{
        display: 'flex',
        alignItems: 'center',
        padding: '8px 16px',
        boxSizing: 'border-box',
        width: '100%',
        height: '52px',
      }}
    >
      <label
        htmlFor="file"
        css={{
          marginLeft: '-16px',
          background: 'transparent',
          cursor: 'pointer',
          '&:hover': {
            opacity: theme.opacity.hover,
          },
          '&:active': {
            opacity: theme.opacity.press,
          },
        }}
      >
        {children}
      </label>
      <input
        onChange={onFileChange}
        type="file"
        id="file"
        css={{ display: 'none' }}
      />
      <ErrorMessages fileStatus={fileStatus} />
    </div>
  );
};
