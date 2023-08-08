import { useTheme } from '@emotion/react';
import { Button } from '@components/common/Button';
import { ReactComponent as Edit } from '@assets/icons/edit.svg';
import { ReactComponent as Archive } from '@assets/icons/archive.svg';

type Props = {
  title: string;
  id: number;
};

export const PostInformationHeader: React.FC<Props> = ({
  title,
  id,
}: Props) => {
  const theme = useTheme() as any;

  return (
    <div
      css={{
        width: '100%',
        display: 'flex',
        justifyContent: 'space-between',
      }}
    >
      <div
        css={{
          display: 'flex',
          gap: '8px',
        }}
      >
        <h2
          css={{
            color: theme.neutral.text.strong,
            font: theme.fonts.displayBold32,
          }}
        >
          {title}FE 이슈트래커 디자인 시스템 구현
        </h2>
        <span
          css={{
            color: theme.neutral.text.weak,
            font: theme.fonts.displayBold32,
          }}
        >
          #2{id}
        </span>
      </div>
      <div
        css={{
          display: 'flex',
          gap: '16px',
          alignItems: 'center',
        }}
      >
        <Button typeVariant="outline" size="S">
          <Edit stroke={theme.brand.text.weak} />
          제목 편집
        </Button>
        <Button typeVariant="outline" size="S">
          <Archive stroke={theme.brand.text.weak} />
          이슈 닫기
        </Button>
      </div>
    </div>
  );
};
