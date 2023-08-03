import { useTheme } from '@emotion/react';
import { Button } from './Button';
import { ReactComponent as Search } from '@assets/icons/search.svg';
import { ReactComponent as ChevronDown } from '@assets/icons/chevronDown.svg';
import { InputContainer } from './textInput/InputContainer';
import { Input } from './textInput/Input';

type Props = {
  filterString: string;
  onClick: () => void;
  onChange: (value: string) => void;
};

export const FilterBar: React.FC<Props> = ({
  filterString,
  onClick,
  onChange,
}) => {
  const theme = useTheme() as any;

  return (
    <InputContainer height={40} radius="m" hasborderColor>
      <div
        css={{
          width: '100%',
          height: '100%',
          display: 'flex',
        }}
      >
        <Button
          typeVariant="ghost"
          onClick={onClick}
          css={{
            width: '128px',
            minWidth: '128px',

            borderRadius: `${theme.radius.m} 0 0 ${theme.radius.m}`,
            backgroundColor: theme.neutral.surface.default,
            borderRight: `${theme.border.default} ${theme.neutral.border.default}`,

            '&:focus-within': {
              backgroundColor: theme.neutral.surface.strong,
            },
          }}
        >
          <span css={{ textAlign: 'left' }}>필터</span>
          <ChevronDown stroke={theme.neutral.text.default} />
        </Button>

        <div
          css={{
            width: '100%',
            display: 'flex',
            alignItems: 'center',
            padding: '0px 25px',
            gap: '8px',
          }}
        >
          <Search stroke={theme.neutral.text.default} />
          <Input
            value={filterString}
            onChange={onChange}
            placeholder="Search all issues"
          />
        </div>
      </div>
    </InputContainer>
  );
};
