import { useTheme } from '@emotion/react';

type ErrorMessageProps = {
  message: string;
  color: string;
};

type DefaultFileStatusType = {
  typeError: boolean;
  sizeError: boolean;
  isUploading: boolean;
  uploadFailed: boolean;
};

type Props = {
  fileStatus: DefaultFileStatusType;
};

export const ErrorMessages: React.FC<Props> = ({ fileStatus }) => {
  const theme = useTheme() as any;

  return (
    <>
      {fileStatus.isUploading && (
        <ErrorMessage
          color={theme.neutral.text.weak}
          message="이미지 업로드 중입니다요.."
        />
      )}
      {fileStatus.typeError && (
        <ErrorMessage
          color={theme.danger.text.default}
          message="이미지 형식만 업로드 할 수 있습니다."
        />
      )}
      {fileStatus.sizeError && (
        <ErrorMessage
          color={theme.danger.text.default}
          message="1MB 이하의 이미지만 업로드 할 수 있습니다."
        />
      )}
      {fileStatus.uploadFailed && (
        <ErrorMessage
          color={theme.danger.text.default}
          message="이미지 업로드에 실패했습니다. 다시 시도해 주세요."
        />
      )}
    </>
  );
};

export const ErrorMessage: React.FC<ErrorMessageProps> = ({
  message,
  color,
}) => {
  const theme = useTheme() as any;

  return (
    <span
      css={{
        color: color,
        font: theme.fonts.availableMedium12,
      }}
    >
      {message}
    </span>
  );
};
