import { useTheme } from '@emotion/react';

type Props = {
  size?: 'defaultSize' | 'S';
  isDisabled?: boolean;
  letterCount?: number;
  textAreaValue: string;
  onChangeTextArea: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  onAddFileUrl: (fileName: string, fileUrl: string) => void;
};

export const TextArea: React.FC<Props> = ({
  size = 'defaultSize',
  isDisabled = false,
  letterCount,
  textAreaValue,
  onChangeTextArea,
  onAddFileUrl,
}) => {
  return <></>;
};
