import { useTheme } from '@emotion/react';
import { ReactComponent as Search } from '@assets/icons/search.svg';
import { Input } from './textInput/Input';
import { useLocation } from 'react-router-dom';
import { useState } from 'react';
import { DropDownPanel } from './dropDown/DropDownPanel';
import { DropDownList } from './dropDown/DropDownList';
import { DropDownContainer } from './dropDown/DropDownContainer';
import { getLocalStorageLoginId } from '@utils/localStorage';

type Props = {
  filterValue: string;
  onChangeFilterValue: (value: string) => void;
  goToFilteredPage: (filterValue: string) => void;
};

export const FilterBar: React.FC<Props> = ({
  filterValue,
  onChangeFilterValue,
  goToFilteredPage,
}) => {
  const theme = useTheme() as any;
  const [isPanelOpen, setIsPanelOpen] = useState(false);
  const location = useLocation();

  const loginId = (() => {
    try {
      const loginId = getLocalStorageLoginId();

      return loginId;
    } catch {
      alert('다시 로그인 해주세요.');
      goToFilteredPage('/sign');
    }
  })() as string;

  const closePanel = () => {
    setIsPanelOpen(false);
  };

  const decodedSearch = decodeURIComponent(location.search);
  const filterOptions = [
    {
      id: 1,
      name: '열린 이슈',

      onClick: () => {
        goToFilteredPage(location.search + ' status:open');
        closePanel();
      },

      isSelected:
        location.search === '' || decodedSearch.includes('status:open'),
    },
    {
      id: 2,
      name: '내가 작성한 이슈',

      onClick: () => {
        goToFilteredPage(`${location.search} author:${loginId}`);
        closePanel();
      },

      isSelected: decodedSearch.includes(`author:${loginId}`),
    },
    {
      id: 3,
      name: '나에게 할당된 이슈',

      onClick: () => {
        goToFilteredPage(`${location.search} assignee:${loginId}`);
        closePanel();
      },

      isSelected: decodedSearch.includes(`assignee:${loginId}`),
    },
    {
      id: 4,
      name: '내가 댓글을 남긴 이슈',

      onClick: () => {
        goToFilteredPage(`${location.search} commentAuthor:${loginId}`);
        closePanel();
      },

      isSelected: decodedSearch.includes(`commentAuthor:${loginId}`),
    },
    {
      id: 5,
      name: '닫힌 이슈',

      onClick: () => {
        goToFilteredPage(location.search + ' status:closed');
        closePanel();
      },

      isSelected: decodedSearch.includes('status:closed'),
    },
  ];

  return (
    <div
      css={{
        height: '40px',
        borderRadius: theme.radius.m,
        backgroundColor: theme.neutral.surface.bold,
        gap: '8px',
        border: `${theme.border.default} ${theme.neutral.border.default}`,

        '&:focus-within': {
          backgroundColor: theme.neutral.surface.strong,
          border: `${theme.border.default} ${theme.neutral.border.defaultActive}`,
        },
      }}
    >
      <div
        css={{
          width: '100%',
          height: '100%',
          display: 'flex',
        }}
      >
        <div
          css={{
            width: '128px',
            minWidth: '128px',

            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',

            borderRadius: `${theme.radius.m} 0 0 ${theme.radius.m}`,
            backgroundColor: theme.neutral.surface.default,
            borderRight: `${theme.border.default} ${theme.neutral.border.default}`,

            '&:focus-within': {
              backgroundColor: theme.neutral.surface.strong,
            },
          }}
        >
          <DropDownContainer
            {...{
              size: 'M',
              indicator: '필터',
              isPanelOpen,
              onClick: () => {
                setIsPanelOpen(true);
              },
            }}
          >
            <DropDownPanel
              {...{
                position: 'left',
                panelHeader: '이슈 필터',
                onOutsideClick: closePanel,
              }}
            >
              {filterOptions.map(({ id, name, onClick, isSelected }) => (
                <DropDownList
                  key={id}
                  {...{ item: { id, name }, onClick, isSelected }}
                />
              ))}
            </DropDownPanel>
          </DropDownContainer>
        </div>

        <form
          css={{
            width: '100%',
            display: 'flex',
            alignItems: 'center',
            padding: '0px 25px',
            gap: '8px',
          }}
          onSubmit={(event) => {
            event.preventDefault();

            goToFilteredPage(filterValue);
          }}
        >
          <Search stroke={theme.neutral.text.default} />
          <Input
            value={filterValue}
            placeholder="Search all issues"
            onChange={onChangeFilterValue}
          />
        </form>
      </div>
    </div>
  );
};
