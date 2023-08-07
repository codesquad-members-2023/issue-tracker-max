import { useTheme } from '@emotion/react';

type DefaultFileStatusType = {
  typeError: boolean;
  sizeError: boolean;
  isUploading: boolean;
  uploadFailed: boolean;
};

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
      {fileStatus.isUploading && (
        <span
          css={{
            color: theme.neutral.text.weak,
            font: theme.fonts.availableMedium12,
          }}
        >
          이미지 업로드 중입니다요..
        </span>
      )}
      {fileStatus.typeError && (
        <span
          css={{
            color: theme.danger.text.default,
            font: theme.fonts.availableMedium12,
          }}
        >
          이미지 형식만 업로드 할 수 있습니다.
        </span>
      )}
      {fileStatus.sizeError && (
        <span
          css={{
            color: theme.danger.text.default,
            font: theme.fonts.availableMedium12,
          }}
        >
          1MB 이하의 이미지만 업로드 할 수 있습니다.
        </span>
      )}
      {fileStatus.uploadFailed && (
        <span
          css={{
            color: theme.danger.text.default,
            font: theme.fonts.availableMedium12,
          }}
        >
          이미지 업로드에 실패했습니다. 다시 시도해 주세요.
        </span>
      )}
    </div>
  );
};
