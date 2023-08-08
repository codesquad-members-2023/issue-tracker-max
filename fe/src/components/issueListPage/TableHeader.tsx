import { ReactComponent as CheckBoxInitial } from '@assets/icons/checkBoxInitial.svg';
import { ReactComponent as AlertCircle } from '@assets/icons/alertCircle.svg';
import { ReactComponent as Archive } from '@assets/icons/archive.svg';
import { Button } from '@components/common/Button';
import { useTheme } from '@emotion/react';
import { DropDownPanel } from '@components/common/dropDown/DropDownPanel';
import { useNavigate } from 'react-router-dom';
import { generateEncodedQuery } from '@utils/generateEncodedQuery';
import { useState } from 'react';
import { DropDownContainer } from '@components/common/dropDown/DropDownContainer';

type Props = {
  openIssueCount: number;
  closedIssueCount: number;
};

export const TableHeader: React.FC<Props> = ({
  openIssueCount = 0,
  closedIssueCount = 0,
}) => {
  const theme = useTheme() as any;
  const [panelOpenStatus, setPanelOpenStatus] = useState({
    assignees: false,
    label: false,
    milestone: false,
    author: false,
  });
  const navigate = useNavigate();

  const onIssueFilterClick = (queryValue: 'open' | 'closed') => {
    const query = generateEncodedQuery('status', queryValue);

    navigate(query);
  };

  return (
    <div
      css={{ width: '100%', display: 'flex', justifyContent: 'space-between' }}
    >
      <div css={{ display: 'flex', alignItems: 'center' }}>
        <CheckBoxInitial
          width={16}
          height={16}
          stroke={theme.neutral.border.default}
          css={{
            padding: '0px 32px',
            cursor: 'pointer',

            '&: hover': {
              opacity: theme.opacity.hover,
            },
          }}
        />

        <div css={{ display: 'flex', gap: '24px', textWrap: 'nowrap' }}>
          <Button
            typeVariant="ghost"
            onClick={() => onIssueFilterClick('open')}
          >
            <AlertCircle stroke={theme.neutral.text.strong} />
            <span
              css={{ font: theme.fonts.availableMedium16 }}
            >{`열린 이슈 (${openIssueCount})`}</span>
          </Button>
          <Button
            typeVariant="ghost"
            onClick={() => onIssueFilterClick('closed')}
          >
            <Archive stroke={theme.neutral.text.strong} />
            <span
              css={{ font: theme.fonts.availableMedium16 }}
            >{`닫힌 이슈 (${closedIssueCount})`}</span>
          </Button>
        </div>
      </div>

      <div
        css={{
          display: 'flex',
          gap: '32px',
          marginRight: '32px',
        }}
      >
        <DropDownContainer
          size="M"
          indicator="담당자"
          isPanelOpen={panelOpenStatus.assignees}
        >
          <DropDownPanel
            position="right"
            panelHeader="담당자 필터"
            onOutsideClick={() =>
              setPanelOpenStatus((prev) => ({ ...prev, assignees: false }))
            }
          >
            <div></div>
          </DropDownPanel>
        </DropDownContainer>
        <DropDownContainer
          size="M"
          indicator="레이블"
          isPanelOpen={panelOpenStatus.label}
        >
          <DropDownPanel
            position="right"
            panelHeader="레이블 필터"
            onOutsideClick={() =>
              setPanelOpenStatus((prev) => ({ ...prev, label: false }))
            }
          >
            <div></div>
          </DropDownPanel>
        </DropDownContainer>
        <DropDownContainer
          size="M"
          indicator="마일스톤"
          isPanelOpen={panelOpenStatus.milestone}
        >
          <DropDownPanel
            position="right"
            panelHeader="마일스톤 필터"
            onOutsideClick={() =>
              setPanelOpenStatus((prev) => ({ ...prev, milestone: false }))
            }
          >
            <div></div>
          </DropDownPanel>
        </DropDownContainer>
        <DropDownContainer
          size="M"
          indicator="작성자"
          isPanelOpen={panelOpenStatus.author}
        >
          <DropDownPanel
            position="right"
            panelHeader="작성자 필터"
            onOutsideClick={() =>
              setPanelOpenStatus((prev) => ({ ...prev, author: false }))
            }
          >
            <div></div>
          </DropDownPanel>
        </DropDownContainer>
      </div>
    </div>
  );
};
