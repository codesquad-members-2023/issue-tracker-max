import { useTheme } from '@emotion/react';
import { Input } from './textInput/Input';
import { InputContainer } from './textInput/InputContainer';
import { ReactComponent as RefreshCcw } from '@assets/icons/refreshCcw.svg';

type Props = {
  value: string;
  onChange: (value: string) => void;
  onRandomButtonClick: () => void;
};

export const ColorCodeInput: React.FC<Props> = ({
  value,
  onChange,
  onRandomButtonClick,
}) => {
  const theme = useTheme() as any;

  return (
    <InputContainer height={40}>
      <div
        css={{
          minWidth: '64px',
          font: theme.fonts.displayMedium12,
          color: theme.neutral.text.weak,
          userSelect: 'none',
        }}
      >
        배경 색상
      </div>

      <Input {...{ value, onChange }} />

      <RefreshCcw
        stroke={theme.neutral.text.default}
        onClick={onRandomButtonClick}
        css={{
          minWidth: '16px',
          cursor: 'pointer',
        }}
      />
    </InputContainer>
  );
};
